package com.trevorism.gcloud.webapi.controller

import com.trevorism.gcloud.webapi.model.AssessmentSubmission
import com.trevorism.gcloud.webapi.service.AssessmentService
import com.trevorism.gcloud.webapi.service.SubmissionRateLimiter
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.inject.Inject

import java.time.Instant

@Controller("/api/assessment")
class AssessmentController {

    static final String APP_ENGINE_CLIENT_ADDRESS = "X-AppEngine-User-IP"

    @Inject
    AssessmentService assessmentService

    @Inject
    SubmissionRateLimiter rateLimiter

    @Tag(name = "Assessment Operations")
    @Operation(summary = "Request a free assessment. Open to anonymous visitors and rate limited per client address")
    @Post(value = "/", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    HttpResponse<?> submit(@Body AssessmentSubmission submission, HttpRequest<?> request) {
        if (!rateLimiter.tryAcquire(clientAddress(request), Instant.now())) {
            return HttpResponse.status(HttpStatus.TOO_MANY_REQUESTS)
        }
        if (submission?.website?.trim()) {
            return HttpResponse.accepted()
        }
        if (!assessmentService.isValid(submission)) {
            return HttpResponse.badRequest()
        }
        assessmentService.submit(submission)
        return HttpResponse.accepted()
    }

    private static String clientAddress(HttpRequest<?> request) {
        String appEngineAddress = request.headers.get(APP_ENGINE_CLIENT_ADDRESS)
        if (appEngineAddress) {
            return appEngineAddress
        }
        return request.remoteAddress?.address?.hostAddress ?: "unknown"
    }
}
