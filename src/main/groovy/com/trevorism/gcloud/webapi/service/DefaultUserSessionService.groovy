package com.trevorism.gcloud.webapi.service

import com.google.gson.Gson
import com.trevorism.data.FastDatastoreRepository
import com.trevorism.data.Repository
import com.trevorism.data.model.filtering.FilterBuilder
import com.trevorism.data.model.filtering.SimpleFilter
import com.trevorism.gcloud.webapi.model.*
import com.trevorism.http.HttpClient
import com.trevorism.http.JsonHttpClient
import com.trevorism.https.SecureHttpClient
import com.trevorism.ClaimProperties
import com.trevorism.ClaimsProvider
import com.trevorism.ClasspathBasedPropertiesProvider
import com.trevorism.PropertiesProvider
import jakarta.inject.Inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.time.LocalDateTime

@jakarta.inject.Singleton
class DefaultUserSessionService implements UserSessionService {

    private static final Logger log = LoggerFactory.getLogger(DefaultUserSessionService.class.getName())

    @Inject
    private SecureHttpClient secureHttpClient
    private Repository<ForgotPasswordLink> forgotPasswordLinkRepository
    private Repository<User> repository
    private PropertiesProvider propertiesProvider = new ClasspathBasedPropertiesProvider()

    private final Gson gson = new Gson()

    DefaultUserSessionService(SecureHttpClient secureHttpClient){
        this.secureHttpClient = secureHttpClient
        forgotPasswordLinkRepository = new FastDatastoreRepository<>(ForgotPasswordLink.class, secureHttpClient)
        repository = new FastDatastoreRepository<>(User, secureHttpClient)

    }

    @Override
    String getToken(LoginRequest loginRequest) {
        String json = gson.toJson(TokenRequest.fromLoginRequest(loginRequest))
        try {
            return invokeTokenRequest(json)
        } catch (Exception e) {
            log.debug("Invalid login", e)
        }
        return null
    }

    @Override
    User getUserFromToken(String token) {
        try {
            ClaimProperties claimProperties = ClaimsProvider.getClaims(token, propertiesProvider.getProperty("signingKey"))
            def response = secureHttpClient.get("https://auth.trevorism.com/user/${claimProperties.id}", ["Authorization": "bearer ${token}".toString()])
            User user = gson.fromJson(response.value, User)
            return user
        } catch (Exception e) {
            log.warn("Unable to find user", e)
            return User.NULL_USER
        }
    }

    @Override
    boolean registerUser(RegistrationRequest registrationRequest) {
        if (!validate(registrationRequest)) {
            return false
        }
        String json = gson.toJson(registrationRequest)
        def response = secureHttpClient.post("https://auth.trevorism.com/user", json, [:])
        User user = gson.fromJson(response.value, User)
        return !User.isNullUser(user)
    }

    @Override
    boolean doesUsernameExist(String username) {
        return findUser(new SimpleFilter("username", "=", username.toLowerCase()))
    }

    private User findUser(SimpleFilter simpleFilter) {
        def list = repository.filter(new FilterBuilder().addFilter(simpleFilter).build())
        if (list) {
            return list[0]
        }
        return null
    }

    @Override
    void generateForgotPasswordLink(ForgotPasswordRequest forgotPasswordRequest) {
        List<User> users = repository.list()
        User user = users.find { it.email.toLowerCase() == forgotPasswordRequest.email.toLowerCase() }

        if (!user) {
            throw new RuntimeException("Unable to find user with email ${forgotPasswordRequest.email}")
        }
        if (!user.active) {
            throw new RuntimeException("User is inactive")
        }
        ForgotPasswordLink forgotPasswordLink = new ForgotPasswordLink(username: user.username)

        forgotPasswordLink = forgotPasswordLinkRepository.create(forgotPasswordLink)
        new ForgotPasswordEmailer().sendForgotPasswordEmail(forgotPasswordRequest.email, forgotPasswordLink.username, forgotPasswordLink.toResetUrl())
    }

    @Override
    void resetPassword(String resetId) {
        ForgotPasswordLink link = forgotPasswordLinkRepository.get(resetId)
        if (!link) {
            throw new RuntimeException("Invalid reset request")
        }
        if (link.expireDate.before(new Date())) {
            throw new RuntimeException("Reset link has expired")
        }
        String toPost = gson.toJson(["username": link.username])
        secureHttpClient.post("https://auth.trevorism.com/user/reset", toPost)
        forgotPasswordLinkRepository.delete(resetId)
    }

    @Override
    boolean changePassword(ChangePasswordRequest changePasswordRequest, String token) {
        String json = gson.toJson(changePasswordRequest)
        def response = secureHttpClient.post("https://auth.trevorism.com/user/change", json, ["Authorization": "bearer ${token}".toString()])
        String value = response.value
        return value == "true"
    }

    @Override
    void sendLoginEvent(User user) {
        log.info("User ${user.username} logged in at ${LocalDateTime.now()}")
    }

    private boolean validate(RegistrationRequest registrationRequest) {
        if (!(registrationRequest?.username?.length() >= 3)) {
            return false
        }

        if (!(registrationRequest?.email?.contains("@"))) {
            return false
        }

        if (!(registrationRequest?.password?.length() >= 6)) {
            return false
        }

        if (doesUsernameExist(registrationRequest.username)) {
            return false
        }

        return true
    }

    private String invokeTokenRequest(String json) {
        String result = secureHttpClient.post("https://auth.trevorism.com/token", json, [:]).value
        if (result.startsWith("<html>"))
            throw new RuntimeException("Bad Request to get token")
        log.trace("Successful login, token: ${result}")
        return result
    }
}
