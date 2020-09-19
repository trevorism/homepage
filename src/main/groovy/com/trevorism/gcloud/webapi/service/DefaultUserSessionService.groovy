package com.trevorism.gcloud.webapi.service

import com.google.gson.Gson
import com.trevorism.data.PingingDatastoreRepository
import com.trevorism.data.Repository
import com.trevorism.gcloud.webapi.model.*
import com.trevorism.http.headers.HeadersJsonHttpClient
import com.trevorism.http.util.ResponseUtils
import com.trevorism.secure.ClaimProperties
import com.trevorism.secure.ClaimsProvider
import com.trevorism.secure.PropertiesProvider

import java.util.logging.Logger

class DefaultUserSessionService implements UserSessionService {

    private HeadersJsonHttpClient httpClient = new HeadersJsonHttpClient()
    private final Gson gson = new Gson()
    private static final Logger log = Logger.getLogger(DefaultUserSessionService.class.getName())
    private PropertiesProvider propertiesProvider = new PropertiesProvider()
    private Repository<ForgotPasswordLink> forgotPasswordLinkRepository = new PingingDatastoreRepository<>(ForgotPasswordLink.class)

    @Override
    String getToken(LoginRequest loginRequest) {
        String json = gson.toJson(TokenRequest.fromLoginRequest(loginRequest))
        try {
            String result = ResponseUtils.getEntity(httpClient.post("https://auth.trevorism.com/token", json, [:]))
            if(result.startsWith("<html>"))
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
        String json = gson.toJson(new TokenRequest(id: propertiesProvider.getProperty("clientId"), password: propertiesProvider.getProperty("clientSecret"), type: "app"))
        String token = ResponseUtils.getEntity(httpClient.post("https://auth.trevorism.com/token", json, [:]))
        def response = httpClient.get("https://auth.trevorism.com/user", ["Authorization": "bearer ${token}".toString()])
        List<User> users = gson.fromJson(ResponseUtils.getEntity(response), List.class)
        return users
    }

    @Override
    void generateForgotPasswordLink(ForgotPasswordRequest forgotPasswordRequest) {
        List<User> users = getAllUsers()
        def user = users.find { it.email.toLowerCase() == forgotPasswordRequest.email.toLowerCase() }
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
        String json = gson.toJson(new TokenRequest(id: propertiesProvider.getProperty("clientId"), password: propertiesProvider.getProperty("clientSecret"), type: "app"))
        String token = ResponseUtils.getEntity(httpClient.post("https://auth.trevorism.com/token", json, [:]))
        String toPost = gson.toJson(["username":link.username])
        httpClient.post("https://auth.trevorism.com/user/reset", toPost, ["Authorization": "bearer ${token}".toString()])
        forgotPasswordLinkRepository.delete(resetId)
    }

    @Override
    boolean changePassword(ChangePasswordRequest changePasswordRequest, String token) {
        String json = gson.toJson(changePasswordRequest)
        def response = httpClient.post("https://auth.trevorism.com/user/change", json, ["Authorization": "bearer ${token}".toString()])
        String value = ResponseUtils.getEntity(response)
        return value == "true"
    }

    boolean validate(RegistrationRequest registrationRequest) {
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
