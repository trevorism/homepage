package com.trevorism.gcloud.webapi.service

import com.google.gson.Gson
import com.trevorism.gcloud.webapi.model.*
import com.trevorism.https.SecureHttpClient
import jakarta.inject.Inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@jakarta.inject.Singleton
class DefaultUserSessionService implements UserSessionService {

    private static final Logger log = LoggerFactory.getLogger(DefaultUserSessionService.class.getName())

    @Inject
    private SecureHttpClient secureHttpClient
    private final Gson gson = new Gson()

    DefaultUserSessionService(SecureHttpClient secureHttpClient){
        this.secureHttpClient = secureHttpClient
    }

    @Override
    User getUserFromToken() {
        try {
            def response = secureHttpClient.get("https://auth.trevorism.com/user/me")
            return gson.fromJson(response.value, User)
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
    boolean changePassword(ChangePasswordRequest changePasswordRequest) {
        String json = gson.toJson(changePasswordRequest)
        def response = secureHttpClient.post("https://auth.trevorism.com/user/change", json)
        return response == "true"
    }

    private static boolean validate(RegistrationRequest registrationRequest) {
        if (!(registrationRequest?.username?.length() >= 3)) {
            return false
        }

        if (!(registrationRequest?.email?.contains("@"))) {
            return false
        }

        if (!(registrationRequest?.password?.length() >= 6)) {
            return false
        }

        return true
    }

}
