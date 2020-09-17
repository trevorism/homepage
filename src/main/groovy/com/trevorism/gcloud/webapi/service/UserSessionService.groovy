package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.LoginRequest
import com.trevorism.gcloud.webapi.model.RegistrationRequest
import com.trevorism.gcloud.webapi.model.User

interface UserSessionService {

    String getToken(LoginRequest loginRequest)

    User getUserFromToken(String bearerToken)

    boolean registerUser(RegistrationRequest registrationRequest)

    boolean doesUsernameExist(String username)
}