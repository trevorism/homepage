package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.LoginRequest
import com.trevorism.gcloud.webapi.model.RegistrationRequest
import com.trevorism.gcloud.webapi.model.User
import org.junit.Test

class DefaultUserSessionServiceTest {

    private UserSessionService userSessionService = new DefaultUserSessionService()

    @Test
    void testGetToken() {
        assert !userSessionService.getToken(new LoginRequest(username: "in", password: "valid"))
    }

    @Test
    void testGetUserFromToken() {
        assert User.isNullUser(userSessionService.getUserFromToken("555"))
    }

    @Test
    void testRegisterUser() {
        assert !userSessionService.registerUser(new RegistrationRequest(username: "another", password: "123456", email: "another@trevorism.com"))
    }

    @Test
    void testDoesUsernameExist() {
        assert userSessionService.doesUsernameExist("another")
        assert !userSessionService.doesUsernameExist("ab")
    }

    @Test
    void testValidate() {
        assert !userSessionService.validate(null)
        assert !userSessionService.validate(new RegistrationRequest())
        assert !userSessionService.validate(new RegistrationRequest(username: "ab", password: "123456", email: "test@trevorism.com"))
    }

}
