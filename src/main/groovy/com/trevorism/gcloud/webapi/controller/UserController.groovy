package com.trevorism.gcloud.webapi.controller

import com.trevorism.gcloud.webapi.model.ChangePasswordRequest
import com.trevorism.gcloud.webapi.model.RegistrationRequest
import com.trevorism.gcloud.webapi.model.User
import com.trevorism.gcloud.webapi.service.DefaultUserSessionService
import com.trevorism.gcloud.webapi.service.UserSessionService
import com.trevorism.secure.Roles
import com.trevorism.secure.Secure
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation

import javax.ws.rs.*
import javax.ws.rs.core.Cookie
import javax.ws.rs.core.MediaType

@Api("User Operations")
@Path("/user")
class UserController {

    private UserSessionService userSessionService = new DefaultUserSessionService()

    @ApiOperation(value = "Register a new user")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secure(Roles.USER)
    boolean register(RegistrationRequest registrationRequest) {
        return userSessionService.registerUser(registrationRequest)
    }

    @ApiOperation(value = "Get the current user")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secure(Roles.USER)
    User getUser(@CookieParam("session") Cookie cookie) {
        String value = cookie.value
        return userSessionService.getUserFromToken(value)
    }

    @ApiOperation(value = "Register a new user")
    @POST
    @Path("change")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secure(Roles.USER)
    void changePassword(ChangePasswordRequest request, @CookieParam("session") Cookie cookie) {
        String value = cookie.value
        def result = userSessionService.changePassword(request, value)
        if (!result){
            throw new RuntimeException("Unable to change password successfully")
        }
    }
}
