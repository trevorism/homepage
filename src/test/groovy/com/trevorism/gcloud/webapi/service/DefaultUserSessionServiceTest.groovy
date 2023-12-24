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
        assert User.isNullUser(userSessionService.getUserFromToken("555"))
    }

    @Test
    void testRegisterUser() {
        userSessionService.secureHttpClient = [get: { url -> "[{\"username\":\"another\"}]" }] as SecureHttpClient
        userSessionService.repository = [filter: { f -> [new User()] }] as Repository<User>

        assert !userSessionService.registerUser(new RegistrationRequest(username: "another", password: "123456", email: "another@trevorism.com"))
    }

    @Test
    void testDoesUsernameExist() {
        userSessionService.secureHttpClient = [get: { url -> "[{\"username\":\"another\"}]" }] as SecureHttpClient
        userSessionService.repository = [filter: { f ->
            if (f.simpleFilters[0].value == "another")
                return [new User(username: "another")]
            return null
        }] as Repository<User>
        assert userSessionService.doesUsernameExist("another")
        assert !userSessionService.doesUsernameExist("ab")
    }

    @Test
    void testValidate() {
        userSessionService.secureHttpClient = [get: { url -> "[]" }] as SecureHttpClient
        userSessionService.repository = [filter: { f -> null }] as Repository<User>

        assert !userSessionService.validate(null)
        assert !userSessionService.validate(new RegistrationRequest())
        assert !userSessionService.validate(new RegistrationRequest(username: "ab", password: "123456", email: "test@trevorism.com"))
        assert !userSessionService.validate(new RegistrationRequest(username: "abc", password: "123456", email: "testtrevorism.com"))
        assert !userSessionService.validate(new RegistrationRequest(username: "abc", password: "12456", email: "test@trevorism.com"))
        assert userSessionService.validate(new RegistrationRequest(username: "abc", password: "123456", email: "test@trevorism.com"))
    }

    @Test
    void testChangePassword() {
        userSessionService.secureHttpClient = [post: { url, content, map ->
            return new HeadersHttpResponse("true")
        }] as SecureHttpClient
        assert userSessionService.changePassword(new ChangePasswordRequest(), "token")
    }

    @Test
    void testChangePasswordNotWorking() {
        userSessionService.secureHttpClient = [post: { url, content, map ->
            return new HeadersHttpResponse("blah")
        }] as SecureHttpClient
        assert !userSessionService.changePassword(new ChangePasswordRequest(), "token")
    }

}
