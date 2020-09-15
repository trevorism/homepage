package com.trevorism.gcloud.webapi.controller

import com.google.gson.Gson
import com.trevorism.gcloud.webapi.model.LoginRequest
import com.trevorism.gcloud.webapi.model.TokenRequest
import com.trevorism.gcloud.webapi.model.User
import com.trevorism.http.HttpClient
import com.trevorism.http.JsonHttpClient
import com.trevorism.http.headers.HeadersHttpClient
import com.trevorism.http.headers.HeadersJsonHttpClient
import com.trevorism.http.util.ResponseUtils
import com.trevorism.secure.ClaimProperties
import com.trevorism.secure.ClaimsProvider
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
    HeadersJsonHttpClient headersJsonHttpClient = new HeadersJsonHttpClient()

    @ApiOperation(value = "Login to Trevorism")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response login(LoginRequest loginRequest, @Context HttpServletRequest httpServletRequest) {
        Gson gson = new Gson()
        String json = gson.toJson(TokenRequest.fromLoginRequest(loginRequest))
        String token = httpClient.post("https://auth.trevorism.com/token", json)

        println httpServletRequest.getContextPath()


        if(token) {
            //Only needed if we are refreshing session on insecure server calls
            setTokenOnSession(httpServletRequest, token)

            ClaimProperties claimProperties = ClaimsProvider.getClaims(token)
            def response = headersJsonHttpClient.get("https://auth.trevorism.com/user/${claimProperties.id}", ["Authorization":"bearer ${token}".toString()])
            User user = gson.fromJson(ResponseUtils.getEntity(response), User)

            NewCookie sessionCookie = new NewCookie("session",token,"/",null,null, 15*60,false)
            NewCookie usernameCookie = new NewCookie("user_name", user.getUsername(),"/",null,null, 15*60,false)
            NewCookie adminCookie = new NewCookie("admin", user.admin.toString(),"/",null,null, 15*60,false)

            return Response.ok().entity(user).cookie(sessionCookie, usernameCookie, adminCookie).build()
        }

        return Response.status(401)
    }

    private setTokenOnSession(HttpServletRequest httpServletRequest, String token) {
        httpServletRequest.getSession().setAttribute("session", token)
    }

}
