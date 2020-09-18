package com.trevorism.gcloud.webapi.service

import com.trevorism.event.EventProducer
import com.trevorism.event.PingingEventProducer
import com.trevorism.gcloud.webapi.model.Email

class ForgotPasswordEmailer {

    private static EventProducer<Email> eventProducer = new PingingEventProducer<>()

    static void sendForgotPasswordEmail(String emailAddress, String username, String link){
        Email email = new Email(recipients: emailAddress, subject: "Trevorism: Forgot Password", body: buildBody(username, link))
        eventProducer.sendEvent("email", email)
    }

    private static String buildBody(String username, String link) {
        StringBuilder sb = new StringBuilder()
        sb << "You recently submitted a forgot password request for trevorism.com.\n"
        sb << "Your username is: ${username}\n\n"
        sb << "Click here to reset your password: ${link}\n"
        sb << "The link will expire in 1 hour."
        return sb.toString()
    }
}
