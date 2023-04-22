package com.trevorism.gcloud.webapi.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.cookie.Cookie
import io.micronaut.http.netty.cookies.NettyCookie
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/api/logout")
class LogoutController {

    @Tag(name = "Logout Operations")
    @Operation(summary = "Logout of Trevorism")
    @Post(value = "/", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    HttpResponse logout() {
        def cookie1 = new NettyCookie("session", "").path("/").domain("trevorism.com").maxAge(0).secure(true)
        def cookie2 = new NettyCookie("user_name", "").path("/").domain("trevorism.com").maxAge(0).secure(true)
        def cookie3 = new NettyCookie("admin", "").path("/").domain("trevorism.com").maxAge(0).secure(true)

        return HttpResponse.noContent().cookies([cookie1, cookie2, cookie3] as Set<Cookie>)
    }

}
