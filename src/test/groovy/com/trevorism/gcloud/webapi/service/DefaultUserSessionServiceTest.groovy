package com.trevorism.gcloud.webapi.service

import com.trevorism.data.Repository
import com.trevorism.gcloud.webapi.model.*
import com.trevorism.http.HeadersHttpResponse
import com.trevorism.http.HttpClient
import com.trevorism.https.SecureHttpClient
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertThrows

class DefaultUserSessionServiceTest {

    private UserSessionService userSessionService = new DefaultUserSessionService()

    @Test
    void testGetUserFromToken() {
        assert User.isNullUser(userSessionService.getUserFromToken())
    }

    @Test
    void testRegisterUser() {
        userSessionService.secureHttpClient = [get: { url -> "[{\"username\":\"another\"}]" }, post: {url, json, headers -> new HeadersHttpResponse("{}")}] as SecureHttpClient
        assert !userSessionService.registerUser(new RegistrationRequest(username: "another", password: "123456", email: "another@trevorism.com"))
    }

    @Test
    void testValidate() {
        userSessionService.secureHttpClient = [get: { url -> "[]" }] as SecureHttpClient

        assert !userSessionService.validate(null)
        assert !userSessionService.validate(new RegistrationRequest())
        assert !userSessionService.validate(new RegistrationRequest(username: "ab", password: "123456", email: "test@trevorism.com"))
        assert !userSessionService.validate(new RegistrationRequest(username: "abc", password: "123456", email: "testtrevorism.com"))
        assert !userSessionService.validate(new RegistrationRequest(username: "abc", password: "12456", email: "test@trevorism.com"))
        assert userSessionService.validate(new RegistrationRequest(username: "abc", password: "123456", email: "test@trevorism.com"))
    }

    @Test
    void testChangePassword() {
        userSessionService.secureHttpClient = [post: { url, content ->
            return "true"
        }] as SecureHttpClient
        assert userSessionService.changePassword(new ChangePasswordRequest())
    }

    @Test
    void testChangePasswordNotWorking() {
        userSessionService.secureHttpClient = [post: { url, content ->
            return "blah"
        }] as SecureHttpClient
        assert !userSessionService.changePassword(new ChangePasswordRequest())
    }

}
