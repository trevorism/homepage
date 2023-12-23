package com.trevorism.gcloud.webapi.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
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
    @Post(value = "/")
    HttpResponse logout() {
        def cookie1 = new NettyCookie("session", "").path("/").maxAge(0).secure(true).domain(".trevorism.com")
        def cookie2 = new NettyCookie("user_name", "").path("/").maxAge(0).secure(true).domain(".trevorism.com")
        def cookie3 = new NettyCookie("admin", "").path("/").maxAge(0).secure(true).domain(".trevorism.com")

        return HttpResponse.noContent().cookies([cookie1, cookie2, cookie3] as Set<Cookie>)
    }

}
