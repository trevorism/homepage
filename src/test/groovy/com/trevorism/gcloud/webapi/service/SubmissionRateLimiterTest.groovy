package com.trevorism.gcloud.webapi.service

import org.junit.jupiter.api.Test

import java.time.Instant

class SubmissionRateLimiterTest {

    private static final Instant START = Instant.parse("2026-11-01T12:00:00Z")

    @Test
    void testAnAddressMayMakeAFewSubmissionsAnHour() {
        def limiter = new SubmissionRateLimiter()
        SubmissionRateLimiter.MAX_SUBMISSIONS_PER_WINDOW.times { assert limiter.tryAcquire("203.0.113.7", START) }
        assert !limiter.tryAcquire("203.0.113.7", START)
    }

    @Test
    void testTheLimitResetsOnceTheWindowPasses() {
        def limiter = new SubmissionRateLimiter()
        SubmissionRateLimiter.MAX_SUBMISSIONS_PER_WINDOW.times { limiter.tryAcquire("203.0.113.7", START) }
        assert limiter.tryAcquire("203.0.113.7", START + SubmissionRateLimiter.WINDOW + java.time.Duration.ofSeconds(1))
    }

    @Test
    void testEachAddressHasItsOwnLimit() {
        def limiter = new SubmissionRateLimiter()
        SubmissionRateLimiter.MAX_SUBMISSIONS_PER_WINDOW.times { limiter.tryAcquire("203.0.113.7", START) }
        assert limiter.tryAcquire("198.51.100.4", START)
    }
}
