package com.trevorism.gcloud.webapi.controller

import com.trevorism.gcloud.webapi.model.ForgotPasswordRequest
import com.trevorism.gcloud.webapi.model.LoginRequest
import com.trevorism.gcloud.webapi.model.User
import com.trevorism.gcloud.webapi.service.DefaultUserSessionService
import com.trevorism.gcloud.webapi.service.Localhost
import com.trevorism.gcloud.webapi.service.UserSessionService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation

import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
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
        NewCookie usernameCookie = new NewCookie("user_name", user.getUsername(), "/", domain, null, 15 * 60, secureCookies)
        NewCookie adminCookie = new NewCookie("admin", user.admin.toString(), "/", domain, null, 15 * 60, secureCookies)
        return Response.ok().entity(user).cookie(sessionCookie, usernameCookie, adminCookie).build()
    }

    @ApiOperation(value = "Sends an email for forgotten passwords")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("forgot")
    void forgotPassword(ForgotPasswordRequest request){
        userSessionService.generateForgotPasswordLink(request)
    }

    @ApiOperation(value = "Resets password")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("reset/{guid}")
    void resetPassword(@PathParam("guid") String resetId){
        userSessionService.resetPassword(resetId)
    }
}
