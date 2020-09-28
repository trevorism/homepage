package com.trevorism.gcloud.webapi.controller

import com.trevorism.gcloud.webapi.model.ChangePasswordRequest
import com.trevorism.gcloud.webapi.model.RegistrationRequest
import com.trevorism.gcloud.webapi.model.User
import com.trevorism.gcloud.webapi.service.UserSessionService
import org.junit.Before
import org.junit.Test

import javax.ws.rs.BadRequestException
import javax.ws.rs.core.Cookie

class UserControllerTest {
    private UserController userController

    @Before
    void setup(){
        userController = new UserController()
        userController.userSessionService = [registerUser: {rr -> rr.username != null}, getUserFromToken: {val -> User.NULL_USER}, changePassword: {r,c -> r.username != null}] as UserSessionService
    }

    @Test
    void testRegister() {
        assert userController.register(new RegistrationRequest(username: "test", password: "test123", email: "test@trevorism.com"))
    }

    @Test(expected = BadRequestException)
    void testRegisterInvalid() {
        println userController.register(new RegistrationRequest())
    }

    @Test
    void testGetUser() {
        assert userController.getUser(new Cookie("session","value"))
    }

    @Test
    void testChangePassword() {
        assert userController.changePassword(new ChangePasswordRequest(username: "test", desiredPassword: "test321", currentPassword: "test123"), new Cookie("session","value"))
    }

    @Test(expected = BadRequestException)
    void testChangePasswordInvalid() {
        assert userController.changePassword(new ChangePasswordRequest(), new Cookie("session","value"))
    }
}
