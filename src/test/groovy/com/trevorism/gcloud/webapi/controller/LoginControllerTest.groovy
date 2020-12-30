package com.trevorism.gcloud.webapi.controller

import com.trevorism.gcloud.webapi.model.ForgotPasswordRequest
import com.trevorism.gcloud.webapi.model.LoginRequest
import com.trevorism.gcloud.webapi.model.User
import com.trevorism.gcloud.webapi.service.UserSessionService
import org.junit.Before
import org.junit.Test

import javax.ws.rs.BadRequestException

class LoginControllerTest {

    private LoginController loginController

    @Before
    void setup() {
        loginController = new LoginController()
        loginController.userSessionService = [getToken: { lr -> lr.username },
                                              getUserFromToken: { token -> new User(username: token) },
                                              generateForgotPasswordLink: { val -> if (!val) throw new RuntimeException() },
                                              resetPassword: { r -> if (!r) throw new RuntimeException() },
                                              sendLoginEvent: { user -> }] as UserSessionService
    }

    @Test
    void testLogin() {
        def response = loginController.login(new LoginRequest(username: "test", password: "test123"))
        assert response
        assert response.status == 200
    }

    @Test
    void testLoginInvalid() {
        def response = loginController.login(new LoginRequest(username: null, password: "test123"))
        assert response
        assert response.status == 400
    }

    @Test
    void testForgotPassword() {
        loginController.forgotPassword(new ForgotPasswordRequest(email: "test@trevorism.com"))
        assert true
    }

    @Test(expected = BadRequestException)
    void testForgotPasswordInvalid() {
        loginController.forgotPassword(null)
        assert true
    }

    @Test
    void testResetPassword() {
        loginController.resetPassword("1234592349")
        assert true
    }

    @Test(expected = BadRequestException)
    void testResetPasswordInvalid() {
        loginController.resetPassword(null)
        assert true
    }
}
