package com.trevorism.gcloud.webapi.controller

import com.trevorism.gcloud.webapi.model.ForgotPasswordRequest
import com.trevorism.gcloud.webapi.model.LoginRequest
import com.trevorism.gcloud.webapi.model.User
import com.trevorism.gcloud.webapi.service.DefaultUserSessionService
import com.trevorism.gcloud.webapi.service.Localhost
import com.trevorism.gcloud.webapi.service.UserSessionService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.NewCookie
import javax.ws.rs.core.Response

@Api("Login Operations")
@Path("/login")
class LoginController {

    private UserSessionService userSessionService = new DefaultUserSessionService()

    @ApiOperation(value = "Login to Trevorism")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = [@ApiResponse(code = 200, message = "Login Successful"),
            @ApiResponse(code = 400, message = "Failed to login")])
    Response login(LoginRequest loginRequest) {
        String token = userSessionService.getToken(loginRequest)
        if (!token) {
            return Response.status(400).build()
        }

        User user = userSessionService.getUserFromToken(token)
        if (User.isNullUser(user)) {
            return Response.status(400).build()
        }

        boolean secureCookies = !Localhost.isLocal()
        String domain = Localhost.isLocal() ? null : "trevorism.com"
        NewCookie sessionCookie = new NewCookie("session", token, "/", domain, null, 15 * 60, secureCookies, true)
        NewCookie usernameCookie = new NewCookie("user_name", loginRequest.username, "/", domain, null, 15 * 60, secureCookies)
        NewCookie adminCookie = new NewCookie("admin", user.admin.toString(), "/", domain, null, 15 * 60, secureCookies)
        return Response.ok().entity(user).cookie(sessionCookie, usernameCookie, adminCookie).build()
    }

    @ApiOperation(value = "Sends an email for forgotten passwords")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("forgot")
    @ApiResponses(value = [@ApiResponse(code = 204, message = "Successfully sent forgot password request"),
            @ApiResponse(code = 400, message = "Failed to send the forgot password request")])
    void forgotPassword(ForgotPasswordRequest request) {
        try {
            userSessionService.generateForgotPasswordLink(request)
        } catch (Exception e) {
            throw new BadRequestException(e)
        }
    }

    @ApiOperation(value = "Resets password")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("reset/{guid}")
    @ApiResponses(value = [@ApiResponse(code = 204, message = "Successfully sent reset request"),
            @ApiResponse(code = 400, message = "Failed to send the reset request")])
    void resetPassword(@PathParam("guid") String resetId) {
        try {
            userSessionService.resetPassword(resetId)
        } catch (Exception e) {
            throw new BadRequestException(e)
        }
    }
}
