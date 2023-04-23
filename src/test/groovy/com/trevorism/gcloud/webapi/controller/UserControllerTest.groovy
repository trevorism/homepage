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
        userController.userSessionService = [registerUser: { rr -> rr.username != null }, getUserFromToken: { val -> User.NULL_USER }, changePassword: { r, c -> r.username != null }] as UserSessionService
    }

    @Test
    void testRegister() {
        assert userController.register(new RegistrationRequest(username: "test", password: "test123", email: "test@trevorism.com"))
    }

    @Test
    void testRegisterInvalid() {
        assertThrows(HttpResponseException, () -> userController.register(new RegistrationRequest()))
    }

    @Test
    void testGetUser() {
        def request = HttpRequestFactory.INSTANCE.get("/")
        request.cookie(new NettyCookie("session", "value"))

        assert userController.getUser(request)
    }

    @Test
    void testChangePassword() {
        def request = HttpRequestFactory.INSTANCE.post("/change", new ChangePasswordRequest(username: "test", desiredPassword: "test321", currentPassword: "test123"))
        request.cookie(new NettyCookie("session", "value"))

        assert userController.changePassword(new ChangePasswordRequest(username: "test", desiredPassword: "test321", currentPassword: "test123"), request)
    }

    @Test
    void testChangePasswordInvalid() {
        def request = HttpRequestFactory.INSTANCE.post("/change", new ChangePasswordRequest())
        request.cookie(new NettyCookie("session", "value"))

        assertThrows(HttpResponseException, () ->
                userController.changePassword(new ChangePasswordRequest(), request))
    }
}
