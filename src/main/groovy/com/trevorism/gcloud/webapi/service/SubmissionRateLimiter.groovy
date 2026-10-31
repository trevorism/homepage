package com.trevorism.gcloud.webapi.service

import jakarta.inject.Singleton

import java.time.Duration
import java.time.Instant

@Singleton
class SubmissionRateLimiter {

    static final int MAX_SUBMISSIONS_PER_WINDOW = 5
    static final Duration WINDOW = Duration.ofHours(1)

    private final Map<String, List<Instant>> submissionsByAddress = [:]

    synchronized boolean tryAcquire(String address, Instant now) {
        Instant windowStart = now.minus(WINDOW)
        submissionsByAddress.values().each { List<Instant> submissions -> submissions.removeIf { it.isBefore(windowStart) } }
        submissionsByAddress.values().removeIf { it.isEmpty() }

        List<Instant> recent = submissionsByAddress.computeIfAbsent(address) { [] }
        if (recent.size() >= MAX_SUBMISSIONS_PER_WINDOW) {
            return false
        }
        recent << now
        return true
    }
}
