package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.AssessmentRequest
import com.trevorism.gcloud.webapi.model.AssessmentSubmission

interface AssessmentService {

    boolean isValid(AssessmentSubmission submission)

    AssessmentRequest submit(AssessmentSubmission submission)

}
