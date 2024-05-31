package com.trevorism.gcloud.webapi.controller

import com.trevorism.gcloud.webapi.model.ChangePasswordRequest
import com.trevorism.gcloud.webapi.model.RegistrationRequest
import com.trevorism.gcloud.webapi.model.User
import com.trevorism.gcloud.webapi.service.UserSessionService
import com.trevorism.secure.Roles
import com.trevorism.secure.Secure
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.inject.Inject
import org.apache.hc.client5.http.HttpResponseException

@Controller("/api/user")
class UserController {

    @Inject
    private UserSessionService userSessionService

    @Tag(name = "User Operations")
    @Operation(summary = "Register a new user")
    @Post(value = "/", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    boolean register(@Body RegistrationRequest registrationRequest) {
        def result = userSessionService.registerUser(registrationRequest)
        if (!result) {
            throw new HttpResponseException(400, "Unable to register user successfully")
        }
        return result
    }

    @Tag(name = "User Operations")
    @Operation(summary = "Get the current user **Secure")
    @Get(value = "/", produces = MediaType.APPLICATION_JSON)
    @Secure(Roles.USER)
    User getUser() {
        return userSessionService.getUserFromToken()
    }

    @Tag(name = "User Operations")
    @Operation(summary = "Change a user's password **Secure")
    @Post(value = "/change", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @Secure(Roles.USER)
    boolean changePassword(@Body ChangePasswordRequest request) {
        def result = userSessionService.changePassword(request)
        if (!result) {
            throw new HttpResponseException(400, "Unable to change password successfully")
        }
        return result
    }
}
