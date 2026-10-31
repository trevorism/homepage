package com.trevorism.gcloud.webapi.service

import com.google.gson.Gson
import com.trevorism.data.Repository
import com.trevorism.gcloud.webapi.model.AssessmentRequest
import com.trevorism.gcloud.webapi.model.AssessmentSubmission
import com.trevorism.gcloud.webapi.model.Mail
import com.trevorism.https.SecureHttpClient
import org.junit.jupiter.api.Test

class DefaultAssessmentServiceTest {

    private static AssessmentSubmission completeSubmission() {
        new AssessmentSubmission(
                name: " Pat ",
                email: "pat@example.com",
                business: "Pat's Bakery, a two-person bakery",
                teamSize: "1-5",
                tools: "Spreadsheets",
                task: "Tracking wholesale orders",
                hosting: "hosted"
        )
    }

    private static DefaultAssessmentService serviceWith(List<AssessmentRequest> stored, List<Map> posts, Closure mailResponse = { "{}" }) {
        DefaultAssessmentService service = new DefaultAssessmentService(null)
        service.@repository = [create: { AssessmentRequest request ->
            request.id = "42"
            stored << request
            request
        }] as Repository<AssessmentRequest>
        service.@appClient = [post: { String url, String body ->
            posts << [url: url, body: body]
            mailResponse()
        }] as SecureHttpClient
        return service
    }

    @Test
    void testACompleteSubmissionIsValid() {
        assert serviceWith([], []).isValid(completeSubmission())
    }

    @Test
    void testOptionalFieldsMayBeLeftOut() {
        def submission = completeSubmission()
        submission.teamSize = null
        submission.tools = null
        submission.hosting = ""
        assert serviceWith([], []).isValid(submission)
    }

    @Test
    void testMissingOrMalformedRequiredFieldsAreInvalid() {
        def service = serviceWith([], [])
        assert !service.isValid(null)
        assert !service.isValid(completeSubmission().tap { name = "  " })
        assert !service.isValid(completeSubmission().tap { email = "not-an-email" })
        assert !service.isValid(completeSubmission().tap { business = null })
        assert !service.isValid(completeSubmission().tap { task = "" })
    }

    @Test
    void testOverlongFieldsAndUnknownChoicesAreInvalid() {
        def service = serviceWith([], [])
        assert !service.isValid(completeSubmission().tap { name = "x" * (DefaultAssessmentService.MAX_NAME_LENGTH + 1) })
        assert !service.isValid(completeSubmission().tap { tools = "x" * (DefaultAssessmentService.MAX_TEXT_LENGTH + 1) })
        assert !service.isValid(completeSubmission().tap { teamSize = "1000" })
        assert !service.isValid(completeSubmission().tap { hosting = "on-premises" })
    }

    @Test
    void testSubmittingStoresTheTrimmedRequest() {
        List<AssessmentRequest> stored = []
        def created = serviceWith(stored, []).submit(completeSubmission())

        assert stored.size() == 1
        assert created.id == "42"
        assert created.name == "Pat"
        assert created.createdDate
    }

    @Test
    void testSubmittingEmailsTheOwnerWithTheRequestDetails() {
        List<Map> posts = []
        serviceWith([], posts).submit(completeSubmission())

        assert posts.size() == 1
        assert posts[0].url == DefaultAssessmentService.MAIL_URL
        Mail mail = new Gson().fromJson(posts[0].body as String, Mail)
        assert mail.recipients == [DefaultAssessmentService.NOTIFICATION_ADDRESS]
        assert mail.subject.contains("Pat's Bakery")
        assert mail.body.contains("pat@example.com")
        assert mail.body.contains("Tracking wholesale orders")
    }

    @Test
    void testAFailedNotificationStillKeepsTheStoredRequest() {
        List<AssessmentRequest> stored = []
        def created = serviceWith(stored, [], { throw new IllegalStateException("mail is down") }).submit(completeSubmission())

        assert created.id == "42"
        assert stored.size() == 1
    }
}
