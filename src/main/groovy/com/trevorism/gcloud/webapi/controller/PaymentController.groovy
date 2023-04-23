package com.trevorism.gcloud.webapi.controller

import com.stripe.Stripe
import com.stripe.model.checkout.Session
import com.stripe.param.checkout.SessionCreateParams
import com.trevorism.gcloud.webapi.model.PaymentRequest
import com.trevorism.ClasspathBasedPropertiesProvider
import com.trevorism.PropertiesProvider
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag


@Controller("/api/payment")
class PaymentController {

    @Tag(name = "Payment Operations")
    @Operation(summary = "Create a new Stripe Payment Session")
    @Post(value = "/session", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    Map createSession(@Body PaymentRequest paymentRequest) {
        if(paymentRequest.dollars < 0.99){
            throw new RuntimeException("Unable to process; insufficient funds for payment")
        }
        PropertiesProvider propertiesProvider = new ClasspathBasedPropertiesProvider()
        Stripe.apiKey = propertiesProvider.getProperty("apiKey")

        SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder().setName("Funding Trevorism").build()
        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmount((long)(paymentRequest.dollars * 100))
                .setProductData(productData)
                .build()
        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(1L)
                .setPriceData(priceData)
                .build()

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://www.trevorism.com/paymentsuccess")
                .setCancelUrl("https://www.trevorism.com/contact")
                .addLineItem(lineItem)
                .build()

        Session session = Session.create(params)
        return [id: session.getId()]
    }
}
