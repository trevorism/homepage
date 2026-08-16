package com.trevorism.gcloud.webapi.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/admin")
class AdminRedirectController {

    static final URI ADMIN_CONSOLE_URI = URI.create("https://admin.auth.trevorism.com")

    @Tag(name = "Admin Operations")
    @Operation(summary = "Redirects the legacy admin route to the admin console")
    @Get(produces = MediaType.ALL)
    HttpResponse redirectToAdminConsole() {
        return HttpResponse.temporaryRedirect(ADMIN_CONSOLE_URI)
    }

}
