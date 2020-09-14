package com.trevorism.gcloud.webapi.controller

import com.google.gson.Gson
import com.trevorism.gcloud.webapi.model.LoginRequest
import com.trevorism.gcloud.webapi.model.TokenRequest
import com.trevorism.http.HttpClient
import com.trevorism.http.JsonHttpClient
import com.trevorism.http.headers.HeadersHttpClient
import com.trevorism.http.headers.HeadersJsonHttpClient
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

@Api("Login Operations")
@Path("login")
class LoginController {

    HttpClient httpClient = new JsonHttpClient()


    @ApiOperation(value = "Login to Trevorism")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response login(LoginRequest loginRequest, @Context HttpServletRequest httpServletRequest) {
        Gson gson = new Gson()
        String json = gson.toJson(TokenRequest.fromLoginRequest(loginRequest))
        String token = httpClient.post("https://auth.trevorism.com/token", json)

        if(token) {
            //Only needed if we are refreshing session on insecure server calls
            httpServletRequest.getSession().setAttribute("session", token)
            NewCookie cookie = new NewCookie("session",token,"/",null,null, 15*60,false)
            return Response.ok().cookie(cookie).build()
        }

        return Response.status(401)
    }

}
