package com.trevorism.gcloud.webapi.filter

import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.MediaType
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.ResponseFilter
import io.micronaut.http.annotation.ServerFilter

/**
 * Controls browser caching of the bundled SPA so new deployments render without a manual refresh.
 *
 * index.html is never cached so it always points at the current build's hashed assets, while the
 * content-hashed /assets/* files are cached indefinitely (their names change each build, so a stale
 * copy can never be referenced).
 *
 * Note: index.html is served from inside the shaded jar, where every build stamps it with the same
 * constant Last-Modified (the 1980 zip epoch). A "no-cache" directive alone is not enough, because
 * revalidation against that frozen timestamp always returns 304 and the browser keeps the stale
 * shell (which references the previous build's assets -> white screen). Using "no-store" and
 * stripping the bogus validators keeps the browser from caching the shell at all.
 */
@ServerFilter("/**")
class CacheControlFilter {

    private static final String IMMUTABLE = "public, max-age=31536000, immutable"
    private static final String NO_STORE = "no-store, no-cache, must-revalidate"

    @ResponseFilter
    void applyCacheControl(HttpRequest<?> request, MutableHttpResponse<?> response) {
        String path = request.path
        if (path.startsWith("/assets/")) {
            setCacheControl(response, IMMUTABLE)
        } else if (acceptsHtml(request)) {
            setCacheControl(response, NO_STORE)
            response.getHeaders().remove(HttpHeaders.LAST_MODIFIED)
            response.getHeaders().remove(HttpHeaders.ETAG)
        }
    }

    private static boolean acceptsHtml(HttpRequest<?> request) {
        request.getHeaders().accept().stream().anyMatch { it.name.contains(MediaType.TEXT_HTML) }
    }

    private static void setCacheControl(MutableHttpResponse<?> response, String value) {
        response.getHeaders().remove(HttpHeaders.CACHE_CONTROL)
        response.getHeaders().add(HttpHeaders.CACHE_CONTROL, value)
    }
}
