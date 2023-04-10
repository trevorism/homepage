package com.trevorism.gcloud.webapi.service

import org.junit.jupiter.api.Test

class ForgotPasswordEmailerTest {

    @Test
    void testSendForgotPasswordEmail() {
        def body = ForgotPasswordEmailer.buildBody("myUsername", "myLink")

        assert body
        assert body.contains("myUsername")
        assert body.contains("myLink")
    }


}
