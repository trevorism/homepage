package com.trevorism.gcloud.webapi.controller

import com.stripe.Stripe
import com.stripe.model.checkout.Session
import com.stripe.param.checkout.SessionCreateParams
import com.trevorism.gcloud.webapi.model.PaymentRequest
import com.trevorism.secure.PropertiesProvider
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation

import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Api("Payment Operations")
@Path("/payment")
class PaymentController {

    private PropertiesProvider propertiesProvider = new PropertiesProvider()

    @ApiOperation(value = "Create a new Stripe Payment Session")
    @POST
    @Path("session")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Map createSession(PaymentRequest paymentRequest) {
        if(paymentRequest.dollars < 0.99){
            throw new RuntimeException("Unable to process; insufficient funds for payment")
        }
        Stripe.apiKey = propertiesProvider.getProperty("stripeApiKey")

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
