package com.trevorism.gcloud.webapi.controller

import com.trevorism.gcloud.webapi.service.Localhost
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag


@Controller("/api/logout")
class LogoutController {

    @Tag(name = "Logout Operations")
    @Operation(summary = "Logout of Trevorism")
    @Post(value = "/", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    HttpResponse logout() {
        //Logout of the session
        HttpResponse response = HttpResponse.noContent()
    }

}
