package com.trevorism.gcloud.webapi.controller

import com.google.gson.Gson
import com.trevorism.gcloud.webapi.model.PaymentRequest
import com.trevorism.https.SecureHttpClient
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.inject.Inject

@Controller("/api/payment")
class PaymentController {

    public static final String BASE_URL = "https://stripe.trade.trevorism.com"

    @Inject
    private SecureHttpClient secureHttpClient
    private Gson gson = new Gson()

    @Tag(name = "Payment Operations")
    @Operation(summary = "Create a new Stripe payment session")
    @Post(value = "/session", produces = MediaType.APPLICATION_JSON)
    Map createSession(@Body PaymentRequest paymentRequest) {
        String json = secureHttpClient.post("$BASE_URL/api/payment/session", gson.toJson(paymentRequest))
        return gson.fromJson(json, Map)
    }
}
