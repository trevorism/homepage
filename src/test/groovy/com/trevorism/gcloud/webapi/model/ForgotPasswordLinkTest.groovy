package com.trevorism.gcloud.webapi.model

import org.junit.Test

class ForgotPasswordLinkTest {

    @Test
    void testToResetUrl() {
        ForgotPasswordLink forgotPasswordLink = new ForgotPasswordLink(id: "test")
        assert "https://www.trevorism.com/api/login/reset/test" == forgotPasswordLink.toResetUrl()
    }
}
