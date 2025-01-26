package com.trevorism.gcloud.webapi.controller

import com.trevorism.http.async.AsyncHttpClient
import com.trevorism.http.async.AsyncJsonHttpClient
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.apache.hc.core5.concurrent.FutureCallback

@Controller("/_ah")
class WarmupController {

    @Tag(name = "Warmup Operations")
    @Operation(summary = "Warms up the application")
    @ApiResponse(responseCode = "200")
    @Get(value = "/warmup")
    HttpResponse warmupAuthService(){
        AsyncHttpClient client = new AsyncJsonHttpClient()
        client.get("https://datastore.trevorism.com/ping", {} as FutureCallback)
        client.get("https://login.auth.trevorism.com/api/ping", {} as FutureCallback)
        client.get("https://auth.trevorism.com/ping", {} as FutureCallback)
        return HttpResponse.ok()
    }

}
