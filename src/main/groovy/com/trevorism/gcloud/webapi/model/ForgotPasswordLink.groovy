package com.trevorism.gcloud.webapi.model

import java.time.Instant
import java.time.ZoneId

class ForgotPasswordLink {

    String id
    String username
    Date expireDate = Instant.now().atZone(ZoneId.systemDefault()).toLocalDateTime().plusHours(1).toDate()

    String toResetUrl() {
        return "https://www.trevorism.com/api/login/reset/${id}"
    }
}
