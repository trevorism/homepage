package com.trevorism.gcloud.webapi.filter

import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.core.Cookie

class TestFilter implements ContainerRequestFilter {

    @Override
    void filter(ContainerRequestContext requestContext) throws IOException {
        Cookie cookie = requestContext.cookies["test-cookie"]
        if (cookie) {
            println cookie.value
        }
        println "no cookie"
    }
}
