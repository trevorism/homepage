package com.trevorism.gcloud.webapi.model

class AssessmentRequest {

    String id
    String name
    String email
    String business
    String teamSize
    String tools
    String task
    String hosting
    Date createdDate

    static AssessmentRequest from(AssessmentSubmission submission, Date createdDate) {
        new AssessmentRequest(
                name: submission.name?.trim() ?: null,
                email: submission.email?.trim(),
                business: submission.business?.trim() ?: null,
                teamSize: submission.teamSize ?: null,
                tools: submission.tools?.trim() ?: null,
                task: submission.task?.trim() ?: null,
                hosting: submission.hosting ?: null,
                createdDate: createdDate
        )
    }
}
