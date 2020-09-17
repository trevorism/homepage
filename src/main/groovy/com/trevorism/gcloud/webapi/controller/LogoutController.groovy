package com.trevorism.gcloud.webapi.controller

import com.trevorism.gcloud.webapi.service.Localhost
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation

import javax.servlet.http.HttpServletRequest
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.NewCookie
import javax.ws.rs.core.Response

@Api("Logout Operations")
@Path("logout")
class LogoutController {

    @ApiOperation(value = "Logout of Trevorism")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response logout(@Context HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().invalidate()
        NewCookie sessionCookie = new NewCookie("session", "", "/", null, null, 15 * 60, !Localhost.isLocal(), false)
        return Response.noContent().cookie(sessionCookie).build()
    }
}
