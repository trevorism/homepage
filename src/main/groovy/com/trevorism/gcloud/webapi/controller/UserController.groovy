package com.trevorism.gcloud.webapi.controller

import com.google.gson.Gson
import com.trevorism.gcloud.webapi.model.User
import com.trevorism.http.headers.HeadersJsonHttpClient
import com.trevorism.http.util.ResponseUtils
import com.trevorism.secure.ClaimProperties
import com.trevorism.secure.ClaimsProvider
import com.trevorism.secure.Roles
import com.trevorism.secure.Secure
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MediaType

@Api("User Operations")
@Path("user")
class UserController {

    HeadersJsonHttpClient headersJsonHttpClient = new HeadersJsonHttpClient()

    @ApiOperation(value = "Secure")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secure(Roles.USER)
    User getUserFromToken(@Context HttpHeaders headers){
        String authorizationHeader = headers.getHeaderString("Authorization")
        String bearerToken = authorizationHeader.substring(7)
        Gson gson = new Gson()
        ClaimProperties claimProperties = ClaimsProvider.getClaims(bearerToken)

        def response = headersJsonHttpClient.get("https://auth.trevorism.com/user/${claimProperties.id}", ["Authorization":authorizationHeader])
        User user = gson.fromJson(ResponseUtils.getEntity(response), User)

        return user
    }
}
