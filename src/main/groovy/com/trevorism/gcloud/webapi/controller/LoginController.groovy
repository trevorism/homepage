package com.trevorism.gcloud.webapi.controller

import com.trevorism.gcloud.webapi.model.ForgotPasswordRequest
import com.trevorism.gcloud.webapi.model.LoginRequest
import com.trevorism.gcloud.webapi.model.User
import com.trevorism.gcloud.webapi.service.DefaultUserSessionService
import com.trevorism.gcloud.webapi.service.UserSessionService
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.apache.hc.client5.http.HttpResponseException

@Controller("/api/login")
class LoginController {

    private UserSessionService userSessionService = new DefaultUserSessionService()

    @Tag(name = "Login Operations")
    @Operation(summary = "Login to Trevorism")
    @Post(value = "/", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    HttpResponse login(@Body LoginRequest loginRequest) {
        String token = userSessionService.getToken(loginRequest)
        if (!token) {
            throw new HttpResponseException(400, "Invalid username or password")
        }

        User user = userSessionService.getUserFromToken(token)
        if (User.isNullUser(user)) {
            throw new HttpResponseException(400, "Unable to find user")
        }

        userSessionService.sendLoginEvent(user)

        //boolean secureCookies = !Localhost.isLocal()
        //String domain = Localhost.isLocal() ? null : "trevorism.com"
        //NettyCookie sessionCookie = new NettyCookie("session", token, "/", domain, null, 15 * 60, secureCookies, true)
        //NettyCookie usernameCookie = new NettyCookie("user_name", loginRequest.username, "/", domain, null, 15 * 60, secureCookies)
        //NettyCookie adminCookie = new NettyCookie("admin", user.admin.toString(), "/", domain, null, 15 * 60, secureCookies)
        //return HttpResponse.ok().entity(user).cookie(sessionCookie, usernameCookie, adminCookie).build()
        return HttpResponse.ok()
    }

    @Tag(name = "Login Operations")
    @Operation(summary = "Sends an email for forgotten passwords")
    @Post(value = "/forgot", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    void forgotPassword(@Body ForgotPasswordRequest request) {
        try {
            userSessionService.generateForgotPasswordLink(request)
        } catch (Exception e) {
            throw new HttpResponseException(400, e.message)
        }
    }

    @Tag(name = "Login Operations")
    @Operation(summary = "Resets password")
    @Get(value = "/reset/{resetId}", produces = MediaType.APPLICATION_JSON)
    void resetPassword(String resetId) {
        try {
            userSessionService.resetPassword(resetId)
        } catch (Exception e) {
            throw new HttpResponseException(400, e.message)
        }
    }
}
