package com.trevorism.gcloud.webapi.service

import com.google.gson.Gson
import com.trevorism.gcloud.webapi.model.LoginRequest
import com.trevorism.gcloud.webapi.model.RegistrationRequest
import com.trevorism.gcloud.webapi.model.TokenRequest
import com.trevorism.gcloud.webapi.model.User
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

    @Override
    String getToken(LoginRequest loginRequest) {
        String json = gson.toJson(TokenRequest.fromLoginRequest(loginRequest))
        try {
            String result = ResponseUtils.getEntity(httpClient.post("https://auth.trevorism.com/token", json, [:]))
            log.fine("Successful login, token: ${result}")
            return result
        } catch (Exception e) {
            log.fine("Invalid login")
            log.finer(e.message);
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

        String json = gson.toJson(new TokenRequest(id: propertiesProvider.getProperty("clientId"), password: propertiesProvider.getProperty("clientSecret"), type: "app"))
        String token = ResponseUtils.getEntity(httpClient.post("https://auth.trevorism.com/token", json, [:]))
        def response = httpClient.get("https://auth.trevorism.com/user", ["Authorization": "bearer ${token}".toString()])
        List<User> user = gson.fromJson(ResponseUtils.getEntity(response), List.class)

        return user.find() { it.username == username }
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
