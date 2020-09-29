package com.trevorism.gcloud.webapi.service

import com.trevorism.data.Repository
import com.trevorism.gcloud.webapi.model.*
import com.trevorism.http.headers.HeadersJsonHttpClient
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.entity.StringEntity
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
        userSessionService.httpClient = [get : { url, map ->
            return ([getEntity: { new StringEntity("[]") }] as CloseableHttpResponse)
        }] as HeadersJsonHttpClient

        assert !userSessionService.validate(null)
        assert !userSessionService.validate(new RegistrationRequest())
        assert !userSessionService.validate(new RegistrationRequest(username: "ab", password: "123456", email: "test@trevorism.com"))
        assert !userSessionService.validate(new RegistrationRequest(username: "abc", password: "123456", email: "testtrevorism.com"))
        assert !userSessionService.validate(new RegistrationRequest(username: "abc", password: "12456", email: "test@trevorism.com"))
        assert userSessionService.validate(new RegistrationRequest(username: "abc", password: "123456", email: "test@trevorism.com"))
    }

    @Test
    void testResetPassword() {
        userSessionService.forgotPasswordLinkRepository = [get: { x -> new ForgotPasswordLink() }, delete: { x -> new ForgotPasswordLink() }] as Repository
        userSessionService.httpClient = [post: { url, content, map ->
            return ([getEntity: { new StringEntity("token") }] as CloseableHttpResponse)
        }] as HeadersJsonHttpClient
        userSessionService.resetPassword("reset")
        assert true
    }

    @Test(expected = RuntimeException)
    void testGenerateForgotPasswordLink() {
        userSessionService.forgotPasswordLinkRepository = [get: { x -> new ForgotPasswordLink() }, delete: { x -> new ForgotPasswordLink() }] as Repository
        userSessionService.httpClient = [get : { url, map ->
            return ([getEntity: { new StringEntity("[]") }] as CloseableHttpResponse)
        },
                                         post: { url, content, map ->
                                             return ([getEntity: { new StringEntity("token") }] as CloseableHttpResponse)
                                         }] as HeadersJsonHttpClient
        userSessionService.generateForgotPasswordLink(new ForgotPasswordRequest(email: "test@trevorism.com"))
    }

    @Test
    void testChangePassword() {
        userSessionService.httpClient = [post: { url, content, map ->
            return ([getEntity: { new StringEntity("true") }] as CloseableHttpResponse)
        }] as HeadersJsonHttpClient
        assert userSessionService.changePassword(new ChangePasswordRequest(), "token")
    }

    @Test
    void testChangePasswordNotWorking() {
        userSessionService.httpClient = [post: { url, content, map ->
            return ([getEntity: { new StringEntity("blah") }] as CloseableHttpResponse)
        }] as HeadersJsonHttpClient
        assert !userSessionService.changePassword(new ChangePasswordRequest(), "token")
    }
}
