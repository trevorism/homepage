package com.trevorism.gcloud.webapi.controller

import com.trevorism.gcloud.webapi.model.ChangePasswordRequest
import com.trevorism.gcloud.webapi.model.RegistrationRequest
import com.trevorism.gcloud.webapi.model.User
import com.trevorism.gcloud.webapi.service.UserSessionService
import io.micronaut.http.HttpRequestFactory
import io.micronaut.http.netty.cookies.NettyCookie
import org.apache.hc.client5.http.HttpResponseException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertThrows

class UserControllerTest {
    private UserController userController

    @BeforeEach
    void setup() {
        userController = new UserController()
        userController.userSessionService = [registerUser: { rr -> rr.username != null }, getUserFromToken: { -> User.NULL_USER }, changePassword: { r -> r.username != null }] as UserSessionService
    }

    @Test
    void testRegisterInvalid() {
        assertThrows(HttpResponseException, () -> userController.register(new RegistrationRequest()))
    }

    @Test
    void testGetUser() {
        assert userController.getUser() == User.NULL_USER
    }

    @Test
    void testChangePassword() {
        assert userController.changePassword(new ChangePasswordRequest(username: "test", desiredPassword: "test321", currentPassword: "test123"))
    }

    @Test
    void testChangePasswordInvalid() {
        assertThrows(HttpResponseException, () ->
                userController.changePassword(new ChangePasswordRequest()))
    }
}
