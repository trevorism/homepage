package com.trevorism.gcloud.webapi.controller

import io.micronaut.http.HttpHeaders
import org.junit.jupiter.api.Test

class AdminRedirectControllerTest {

    @Test
    void testRedirectSendsCallersToTheAdminConsole() {
        AdminRedirectController adminRedirectController = new AdminRedirectController()
        def response = adminRedirectController.redirectToAdminConsole()
        assert response
        assert response.headers.get(HttpHeaders.LOCATION) == "https://admin.auth.trevorism.com"
    }

    @Test
    void testRedirectIsTemporarySoTheShimCanBeRemoved() {
        AdminRedirectController adminRedirectController = new AdminRedirectController()
        def response = adminRedirectController.redirectToAdminConsole()
        assert response.status.code == 307
    }
}
