package com.trevorism.gcloud.webapi.service

import com.google.gson.Gson
import com.trevorism.data.FastDatastoreRepository
import com.trevorism.data.Repository
import com.trevorism.gcloud.webapi.model.AssessmentRequest
import com.trevorism.gcloud.webapi.model.AssessmentSubmission
import com.trevorism.gcloud.webapi.model.Mail
import com.trevorism.gcloud.webapi.model.types.HostingPreferenceType
import com.trevorism.gcloud.webapi.model.types.TeamSizeType
import com.trevorism.https.AppClientSecureHttpClient
import com.trevorism.https.SecureHttpClient
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class DefaultAssessmentService implements AssessmentService {

    private static final Logger log = LoggerFactory.getLogger(DefaultAssessmentService.class.getName())

    static final String MAIL_URL = "https://email.action.trevorism.com/mail"
    static final String NOTIFICATION_ADDRESS = "feedback@trevorism.com"
    static final int MAX_NAME_LENGTH = 100
    static final int MAX_EMAIL_LENGTH = 254
    static final int MAX_BUSINESS_LENGTH = 500
    static final int MAX_TEXT_LENGTH = 2000

    private static final String EMAIL_PATTERN = /^[^\s@]+@[^\s@]+\.[^\s@]+$/

    private SecureHttpClient appClient
    private Repository<AssessmentRequest> repository
    private final Gson gson = new Gson()

    @Inject
    DefaultAssessmentService() {
        this(new AppClientSecureHttpClient())
    }

    DefaultAssessmentService(SecureHttpClient appClient) {
        this.appClient = appClient
        this.repository = new FastDatastoreRepository<>(AssessmentRequest, appClient)
    }

    @Override
    boolean isValid(AssessmentSubmission submission) {
        return submission &&
                isPresent(submission.name, MAX_NAME_LENGTH) &&
                isEmail(submission.email) &&
                isPresent(submission.business, MAX_BUSINESS_LENGTH) &&
                isPresent(submission.task, MAX_TEXT_LENGTH) &&
                (submission.tools ?: "").length() <= MAX_TEXT_LENGTH &&
                isChoiceOrBlank(submission.teamSize, TeamSizeType.ALL) &&
                isChoiceOrBlank(submission.hosting, HostingPreferenceType.ALL)
    }

    @Override
    AssessmentRequest submit(AssessmentSubmission submission) {
        AssessmentRequest created = repository.create(AssessmentRequest.from(submission, new Date()))
        notifyOwner(created)
        return created
    }

    private void notifyOwner(AssessmentRequest request) {
        Mail mail = new Mail(
                subject: "New assessment request from ${request.business}",
                recipients: [NOTIFICATION_ADDRESS],
                body: notificationBody(request)
        )
        try {
            appClient.post(MAIL_URL, gson.toJson(mail))
        } catch (Exception e) {
            log.error("Unable to send the notification for assessment request ${request.id}", e)
        }
    }

    private static String notificationBody(AssessmentRequest request) {
        [
                "A new assessment request arrived. Reply within a week.",
                "",
                "Name: ${request.name}",
                "Email: ${request.email}",
                "Business: ${request.business}",
                "Team size: ${request.teamSize ?: 'not given'}",
                "Tools they pay for: ${request.tools ?: 'not given'}",
                "Task to stop doing by hand: ${request.task}",
                "Where it should run: ${request.hosting ?: 'not given'}",
                "Request id: ${request.id}"
        ].join("\n")
    }

    private static boolean isPresent(String value, int maxLength) {
        String trimmed = value?.trim()
        return trimmed && trimmed.length() <= maxLength
    }

    private static boolean isEmail(String value) {
        String trimmed = value?.trim()
        return trimmed && trimmed.length() <= MAX_EMAIL_LENGTH && trimmed ==~ EMAIL_PATTERN
    }

    private static boolean isChoiceOrBlank(String value, List<String> choices) {
        return !value || value in choices
    }
}
