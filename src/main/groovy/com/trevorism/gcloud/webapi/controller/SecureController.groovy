package com.trevorism.gcloud.webapi.controller

import com.trevorism.http.headers.HeadersHttpClient
import com.trevorism.http.headers.HeadersJsonHttpClient
import com.trevorism.http.util.ResponseUtils
import com.trevorism.secure.Roles
import com.trevorism.secure.Secure
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.entity.StringEntity

import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.NewCookie
import javax.ws.rs.core.Response

@Api("Secure Operations")
@Path("secure")
class SecureController {



    @ApiOperation(value = "Secure")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secure(Roles.ADMIN)
    String secure(@Context HttpHeaders headers){
        return "secure stuff"
    }

    @ApiOperation(value = "Secure")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secure(Roles.USER)
    @Path("user")
    String user(){
        "user stuff here"
    }
}
