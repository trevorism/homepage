package com.trevorism.gcloud.webapi.controller

import org.junit.jupiter.api.Test

class LogoutControllerTest {

    @Test
    void testLogout() {
        LogoutController logoutController = new LogoutController()
        def response = logoutController.logout()
        assert response
        assert response.status.code == 204
    }
}
