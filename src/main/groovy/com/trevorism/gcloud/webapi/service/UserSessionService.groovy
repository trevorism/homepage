package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.ChangePasswordRequest
import com.trevorism.gcloud.webapi.model.ForgotPasswordRequest
import com.trevorism.gcloud.webapi.model.LoginRequest
import com.trevorism.gcloud.webapi.model.RegistrationRequest
import com.trevorism.gcloud.webapi.model.User

interface UserSessionService {

    String getToken(LoginRequest loginRequest)

    User getUserFromToken(String bearerToken)

    boolean registerUser(RegistrationRequest registrationRequest)

    boolean doesUsernameExist(String username)

    void generateForgotPasswordLink(ForgotPasswordRequest forgotPasswordRequest)

    void resetPassword(String resetId)

    boolean changePassword(ChangePasswordRequest changePasswordRequest, String bearerToken)

    void sendLoginEvent(User user)
}