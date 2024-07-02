package com.trevorism.gcloud.webapi.model

class PaymentRequest {

    String name = "Trevorism Funding"
    double dollars
    String successCallbackUrl = "https://trevorism.com"
    String failureCallbackUrl = "https://trevorism.com/contact"

}
