package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.ChangePasswordRequest
import com.trevorism.gcloud.webapi.model.RegistrationRequest
import com.trevorism.gcloud.webapi.model.User

interface UserSessionService {

    User getUserFromToken()

    boolean registerUser(RegistrationRequest registrationRequest)

    boolean changePassword(ChangePasswordRequest changePasswordRequest)

}