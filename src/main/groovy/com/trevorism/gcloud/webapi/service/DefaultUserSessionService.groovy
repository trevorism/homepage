package com.trevorism.gcloud.webapi.service

import com.google.gson.Gson
import com.trevorism.data.FastDatastoreRepository
import com.trevorism.data.Repository
import com.trevorism.data.model.filtering.FilterBuilder
import com.trevorism.data.model.filtering.SimpleFilter
import com.trevorism.gcloud.webapi.model.*
import com.trevorism.https.SecureHttpClient
import com.trevorism.ClaimProperties
import com.trevorism.ClaimsProvider
import com.trevorism.ClasspathBasedPropertiesProvider
import com.trevorism.PropertiesProvider
import jakarta.inject.Inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@jakarta.inject.Singleton
class DefaultUserSessionService implements UserSessionService {

    private static final Logger log = LoggerFactory.getLogger(DefaultUserSessionService.class.getName())

    @Inject
    private SecureHttpClient secureHttpClient
    private Repository<User> repository
    private PropertiesProvider propertiesProvider = new ClasspathBasedPropertiesProvider()

    private final Gson gson = new Gson()

    DefaultUserSessionService(SecureHttpClient secureHttpClient){
        this.secureHttpClient = secureHttpClient
        repository = new FastDatastoreRepository<>(User, secureHttpClient)
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
    boolean changePassword(ChangePasswordRequest changePasswordRequest, String token) {
        String json = gson.toJson(changePasswordRequest)
        def response = secureHttpClient.post("https://auth.trevorism.com/user/change", json, ["Authorization": "bearer ${token}".toString()])
        String value = response.value
        return value == "true"
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
