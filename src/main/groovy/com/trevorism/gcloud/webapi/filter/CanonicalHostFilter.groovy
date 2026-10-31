package com.trevorism.gcloud.webapi.filter

import io.micronaut.core.annotation.Nullable
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.RequestFilter
import io.micronaut.http.annotation.ServerFilter

@ServerFilter("/**")
class CanonicalHostFilter {

    static final String APEX_HOST = "trevorism.com"
    static final String CANONICAL_ORIGIN = "https://www.trevorism.com"

    @RequestFilter
    @Nullable
    HttpResponse<?> redirectApexToCanonicalHost(HttpRequest<?> request) {
        if (!isApexPageRequest(request)) {
            return null
        }
        String query = request.uri.rawQuery ? "?${request.uri.rawQuery}" : ""
        return HttpResponse.redirect(URI.create("${CANONICAL_ORIGIN}${request.uri.rawPath}${query}"))
    }

    private static boolean isApexPageRequest(HttpRequest<?> request) {
        String host = request.headers.get(HttpHeaders.HOST)?.split(':')?.first()?.toLowerCase()
        return host == APEX_HOST &&
                request.method in [HttpMethod.GET, HttpMethod.HEAD] &&
                !request.path.startsWith("/api/")
    }
}
