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
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

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
    @ApiResponses(value = [@ApiResponse(code = 200, message = "Successfully registered user"),
            @ApiResponse(code = 400, message = "Unable to register user")])
    boolean register(RegistrationRequest registrationRequest) {
        def result = userSessionService.registerUser(registrationRequest)
        if (!result) {
            throw new BadRequestException("Unable to change password successfully")
        }
        return result
    }

    @ApiOperation(value = "Get the current user **Secure(User)")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secure(Roles.USER)
    @ApiResponse(code = 200, message = "User found. Returns an empty user if not found")
    User getUser(@CookieParam("session") Cookie cookie) {
        String value = cookie.value
        return userSessionService.getUserFromToken(value)
    }

    @ApiOperation(value = "Change a user's password **Secure(User)")
    @POST
    @Path("change")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secure(Roles.USER)
    @ApiResponses(value = [@ApiResponse(code = 200, message = "Successfully changed password"),
            @ApiResponse(code = 400, message = "Unable to change password")])
    boolean changePassword(ChangePasswordRequest request, @CookieParam("session") Cookie cookie) {
        String value = cookie.value
        def result = userSessionService.changePassword(request, value)
        if (!result) {
            throw new BadRequestException("Unable to change password successfully")
        }
        return result
    }
}
