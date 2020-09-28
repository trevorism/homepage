package com.trevorism.gcloud.webapi.controller

import org.junit.Test

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession
import javax.ws.rs.core.Response

class LogoutControllerTest {

    @Test
    void testLogout() {
        LogoutController logoutController = new LogoutController()
        Response response = logoutController.logout([getSession:{ [invalidate:{}] as HttpSession }] as HttpServletRequest)
        assert response
        assert response.status == 204
    }
}
