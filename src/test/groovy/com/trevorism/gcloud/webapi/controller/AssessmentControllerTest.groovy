package com.trevorism.gcloud.webapi.controller

import com.trevorism.gcloud.webapi.model.AssessmentRequest
import com.trevorism.gcloud.webapi.model.AssessmentSubmission
import com.trevorism.gcloud.webapi.service.AssessmentService
import com.trevorism.gcloud.webapi.service.SubmissionRateLimiter
import com.trevorism.secure.Secure
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import org.junit.jupiter.api.Test

import java.time.Instant

class AssessmentControllerTest {

    private static AssessmentSubmission submission(Map fields = [:]) {
        new AssessmentSubmission([name: "Pat", email: "pat@example.com", business: "Bakery", task: "Orders"] + fields)
    }

    private static AssessmentController controllerWith(List<AssessmentSubmission> submitted, boolean valid = true, boolean allowed = true) {
        new AssessmentController(
                assessmentService: [
                        isValid: { AssessmentSubmission s -> valid },
                        submit : { AssessmentSubmission s -> submitted << s; new AssessmentRequest(id: "1") }
                ] as AssessmentService,
                rateLimiter: new SubmissionRateLimiter() {
                    @Override
                    synchronized boolean tryAcquire(String address, Instant now) { allowed }
                }
        )
    }

    private static HttpRequest<?> request() {
        HttpRequest.POST("/api/assessment", "").header(AssessmentController.APP_ENGINE_CLIENT_ADDRESS, "203.0.113.7")
    }

    @Test
    void testAValidSubmissionIsAcceptedAndSubmitted() {
        List<AssessmentSubmission> submitted = []
        def response = controllerWith(submitted).submit(submission(), request())

        assert response.status == HttpStatus.ACCEPTED
        assert submitted.size() == 1
    }

    @Test
    void testAnInvalidSubmissionIsRejected() {
        List<AssessmentSubmission> submitted = []
        def response = controllerWith(submitted, false).submit(submission(), request())

        assert response.status == HttpStatus.BAD_REQUEST
        assert submitted.isEmpty()
    }

    @Test
    void testABotFillingTheHiddenFieldLooksAcceptedButIsDropped() {
        List<AssessmentSubmission> submitted = []
        def response = controllerWith(submitted).submit(submission(website: "https://spam.example"), request())

        assert response.status == HttpStatus.ACCEPTED
        assert submitted.isEmpty()
    }

    @Test
    void testTooManySubmissionsFromOneAddressAreRefused() {
        List<AssessmentSubmission> submitted = []
        def response = controllerWith(submitted, true, false).submit(submission(), request())

        assert response.status == HttpStatus.TOO_MANY_REQUESTS
        assert submitted.isEmpty()
    }

    @Test
    void testTheEndpointIsOpenToAnonymousVisitorsByDesign() {
        def method = AssessmentController.getMethod("submit", AssessmentSubmission, HttpRequest)
        assert !method.getAnnotation(Secure)
    }
}
