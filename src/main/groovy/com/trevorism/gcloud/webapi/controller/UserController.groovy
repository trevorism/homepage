package com.trevorism.gcloud.webapi.controller

import com.trevorism.gcloud.webapi.model.RegistrationRequest
import com.trevorism.gcloud.webapi.service.DefaultUserSessionService
import com.trevorism.gcloud.webapi.service.UserSessionService
import com.trevorism.secure.Roles
import com.trevorism.secure.Secure
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation

import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
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
}
