package com.trevorism.gcloud.webapi.controller

import com.trevorism.gcloud.webapi.service.PageResolver
import io.micronaut.core.io.ResourceResolver
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.server.types.files.StreamedFile
import org.junit.jupiter.api.Test

class NotFoundRouteControllerTest {

    private final NotFoundRouteController controller = new NotFoundRouteController(
            pageResolver: new PageResolver(new ResourceResolver(), "classpath:test-pages/"))

    @Test
    void testPagesAreServedWhateverTheCallerAccepts() {
        def response = controller.forward(HttpRequest.GET("/docs").accept(MediaType.ALL_TYPE))
        assert response.status == HttpStatus.OK
        assert (response.body() as StreamedFile).mediaType == MediaType.TEXT_HTML_TYPE
    }

    @Test
    void testUnknownPagesAreNotFoundAndShowTheNotFoundPage() {
        def response = controller.forward(HttpRequest.GET("/no-such-page"))
        assert response.status == HttpStatus.NOT_FOUND
        assert response.body() instanceof StreamedFile
    }

    @Test
    void testRequestsForFilesAndApisGetAnEmptyNotFound() {
        [
                HttpRequest.GET("/api/missing"),
                HttpRequest.GET("/assets/missing.js"),
                HttpRequest.GET("/missing.png"),
                HttpRequest.POST("/docs", "")
        ].each {
            def response = controller.forward(it)
            assert response.status == HttpStatus.NOT_FOUND, it.path
            assert !response.body(), it.path
        }
    }
}
