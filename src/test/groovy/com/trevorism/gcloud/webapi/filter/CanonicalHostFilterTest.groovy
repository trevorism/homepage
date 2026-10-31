package com.trevorism.gcloud.webapi.filter

import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import org.junit.jupiter.api.Test

class CanonicalHostFilterTest {

    private final CanonicalHostFilter filter = new CanonicalHostFilter()

    @Test
    void testApexPagesRedirectPermanentlyToTheCanonicalHost() {
        def response = filter.redirectApexToCanonicalHost(
                HttpRequest.GET("/articles/production?ref=x").header(HttpHeaders.HOST, "trevorism.com"))
        assert response.status == HttpStatus.MOVED_PERMANENTLY
        assert response.headers.get(HttpHeaders.LOCATION) == "https://www.trevorism.com/articles/production?ref=x"
    }

    @Test
    void testApexHostIsRecognizedWithAPortAndAnyCase() {
        def response = filter.redirectApexToCanonicalHost(HttpRequest.GET("/").header(HttpHeaders.HOST, "Trevorism.com:443"))
        assert response.headers.get(HttpHeaders.LOCATION) == "https://www.trevorism.com/"
    }

    @Test
    void testRequestsThatMustNotMoveAreLeftAlone() {
        [
                HttpRequest.GET("/docs").header(HttpHeaders.HOST, "www.trevorism.com"),
                HttpRequest.GET("/docs").header(HttpHeaders.HOST, "pr-19-dot-homepage-dot-trevorism.appspot.com"),
                HttpRequest.GET("/api/ping").header(HttpHeaders.HOST, "trevorism.com"),
                HttpRequest.POST("/register", "").header(HttpHeaders.HOST, "trevorism.com"),
                HttpRequest.GET("/docs")
        ].each {
            assert filter.redirectApexToCanonicalHost(it) == null, it.toString()
        }
    }
}
