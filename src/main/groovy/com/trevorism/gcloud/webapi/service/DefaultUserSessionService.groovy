package com.trevorism.gcloud.webapi.service

import com.google.gson.Gson
import com.trevorism.data.PingingDatastoreRepository
import com.trevorism.data.Repository
import com.trevorism.event.EventProducer
import com.trevorism.event.EventhubProducer
import com.trevorism.event.PingingEventProducer
import com.trevorism.gcloud.webapi.model.*
import com.trevorism.http.headers.HeadersJsonHttpClient
import com.trevorism.http.util.ResponseUtils
import com.trevorism.https.DefaultSecureHttpClient
import com.trevorism.https.SecureHttpClient
import com.trevorism.secure.ClaimProperties
import com.trevorism.secure.ClaimsProvider

import java.util.logging.Logger

class DefaultUserSessionService implements UserSessionService {

    private static final Logger log = Logger.getLogger(DefaultUserSessionService.class.getName())

    private HeadersJsonHttpClient httpClient = new HeadersJsonHttpClient()
    private SecureHttpClient secureHttpClient = SecureHttpClientSingleton.getInstance().getSecureHttpClient()
    private Repository<ForgotPasswordLink> forgotPasswordLinkRepository = new PingingDatastoreRepository<>(ForgotPasswordLink.class, secureHttpClient)
    private EventProducer<User> eventProducer = new PingingEventProducer<>(secureHttpClient)
    private final Gson gson = new Gson()

    @Override
    String getToken(LoginRequest loginRequest) {
        String json = gson.toJson(TokenRequest.fromLoginRequest(loginRequest))
        try {
            String result = ResponseUtils.getEntity(httpClient.post("https://auth.trevorism.com/token", json, [:]))
            if (result.startsWith("<html>"))
                throw new RuntimeException("Bad Request to get token")
            log.fine("Successful login, token: ${result}")
            return result
        } catch (Exception e) {
            log.fine("Invalid login")
            log.finer(e.message)
        }
        return null
    }

    @Override
    User getUserFromToken(String token) {
        try {
            ClaimProperties claimProperties = ClaimsProvider.getClaims(token)
            def response = httpClient.get("https://auth.trevorism.com/user/${claimProperties.id}", ["Authorization": "bearer ${token}".toString()])
            User user = gson.fromJson(ResponseUtils.getEntity(response), User)
            return user
        } catch (Exception e) {
            log.warning("Unable to find user: ${e.message}")
            return User.NULL_USER
        }
    }

    @Override
    boolean registerUser(RegistrationRequest registrationRequest) {
        if (!validate(registrationRequest)) {
            return false
        }
        String json = gson.toJson(registrationRequest)
        def response = httpClient.post("https://auth.trevorism.com/user", json, [:])
        User user = gson.fromJson(ResponseUtils.getEntity(response), User)
        return !User.isNullUser(user)
    }

    @Override
    boolean doesUsernameExist(String username) {
        List<User> users = getAllUsers()

        return users.find() { it.username == username }
    }

    private List getAllUsers() {
        def response = secureHttpClient.get("https://auth.trevorism.com/user")
        List<User> users = gson.fromJson(response, List.class)
        return users
    }

    @Override
    void generateForgotPasswordLink(ForgotPasswordRequest forgotPasswordRequest) {
        List<User> users = getAllUsers()
        User user = users.find { it.email.toLowerCase() == forgotPasswordRequest.email.toLowerCase() }
        if (!user) {
            throw new RuntimeException("Unable to find user with email ${forgotPasswordRequest.email}")
        }
        if (!user.active) {
            throw new RuntimeException("User is inactive")
        }
        ForgotPasswordLink forgotPasswordLink = new ForgotPasswordLink(username: user.username)

        forgotPasswordLink = forgotPasswordLinkRepository.create(forgotPasswordLink)
        ForgotPasswordEmailer.sendForgotPasswordEmail(forgotPasswordRequest.email, forgotPasswordLink.username, forgotPasswordLink.toResetUrl())
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
        def response = httpClient.post("https://auth.trevorism.com/user/change", json, ["Authorization": "bearer ${token}".toString()])
        String value = ResponseUtils.getEntity(response)
        return value == "true"
    }

    @Override
    void sendLoginEvent(User user) {
        eventProducer.sendEvent("login", user)
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
}
