package com.trevorism.gcloud.webapi.controller

import com.google.gson.Gson
import com.trevorism.gcloud.webapi.model.TenantRequestInput
import com.trevorism.http.util.HttpResponseBodyException
import com.trevorism.http.util.InvalidRequestException
import com.trevorism.https.SecureHttpClient
import io.micronaut.http.HttpStatus
import io.micronaut.http.exceptions.HttpStatusException
import org.junit.jupiter.api.Test

class SubscribedTenantControllerTest {

    private Gson gson = new Gson()
    private List<Map> posts = []
    private List<String> gets = []

    @Test
    void testRequestTenantForwardsTheInputToTheTenantService() {
        SubscribedTenantController controller = controllerWith('{"id":"req-1","status":"PENDING_PAYMENT"}')

        Map result = controller.requestTenant(new TenantRequestInput(name: "Acme", domain: "acme.com"))

        assert result.id == "req-1"
        assert posts[0].url == "https://tenant.auth.trevorism.com/subscribedtenant/"
        Map body = gson.fromJson(posts[0].body as String, Map)
        assert body.name == "Acme"
        assert body.domain == "acme.com"
    }

    @Test
    void testGetCurrentRequestReadsTheCallersRequest() {
        SubscribedTenantController controller = controllerWith('{"id":"req-1","status":"PROVISIONED"}')

        assert controller.getCurrentRequest().status == "PROVISIONED"
        assert gets[0] == "https://tenant.auth.trevorism.com/subscribedtenant/me"
    }

    @Test
    void testGetCurrentRequestReturnsAnEmptyMapWhenThereIsNoContent() {
        SubscribedTenantController controller = controllerWith("")

        assert controller.getCurrentRequest() == [:]
    }

    @Test
    void testGetSubscriptionReadsTheCallersEntitlement() {
        SubscribedTenantController controller = controllerWith('{"provider":"STRIPE","state":"ACTIVE","paidThrough":"2026-09-22T00:00:00Z"}')

        Map subscription = controller.getSubscription()

        assert subscription.state == "ACTIVE"
        assert gets[0] == "https://tenant.auth.trevorism.com/subscribedtenant/subscription"
    }

    @Test
    void testGetSubscriptionReturnsAnEmptyMapWhenThereIsNoContent() {
        SubscribedTenantController controller = controllerWith("")

        assert controller.getSubscription() == [:]
    }

    @Test
    void testCreateCheckoutSessionReturnsTheStripeSession() {
        SubscribedTenantController controller = controllerWith('{"id":"cs_1","url":"https://checkout.stripe.com/c/pay/cs_1"}')

        Map session = controller.createCheckoutSession("req-1")

        assert session.url == "https://checkout.stripe.com/c/pay/cs_1"
        assert posts[0].url == "https://tenant.auth.trevorism.com/subscribedtenant/req-1/session"
        assert gets.isEmpty()
    }

    @Test
    void testProvisionPostsToTheTenantService() {
        SubscribedTenantController controller = controllerWith('{"id":"req-1","status":"PROVISIONED","tenantGuid":"guid-1"}')

        Map result = controller.provision("req-1")

        assert result.tenantGuid == "guid-1"
        assert posts[0].url == "https://tenant.auth.trevorism.com/subscribedtenant/req-1/tenant"
    }

    @Test
    void testCreateBillingPortalSessionReturnsTheProvidersUrl() {
        SubscribedTenantController controller = controllerWith('{"url":"https://billing.stripe.com/session/abc"}')

        Map session = controller.createBillingPortalSession()

        assert session.url == "https://billing.stripe.com/session/abc"
        assert posts[0].url == "https://tenant.auth.trevorism.com/subscribedtenant/portal"
        assert gets.isEmpty()
    }

    @Test
    void testAnUnreachableBillingProviderIsReportedWithoutBlamingTheHomepage() {
        SubscribedTenantController controller = controllerThatFailsWith(bodyFailure(500,
                '{"message":"Internal Server Error"}'))

        try {
            controller.createBillingPortalSession()
            assert false
        } catch (HttpStatusException e) {
            assert e.status == HttpStatus.BAD_GATEWAY
            assert e.message == "Unable to reach the billing provider"
        }
    }

    @Test
    void testARefusalFromTheTenantServiceIsNotReportedAsAServerError() {
        SubscribedTenantController controller = controllerThatFailsWith(
                new InvalidRequestException(new RuntimeException("Domain acme.com is already in use"), 400))

        try {
            controller.requestTenant(new TenantRequestInput(name: "Acme", domain: "acme.com"))
            assert false
        } catch (HttpStatusException e) {
            assert e.status == HttpStatus.BAD_REQUEST
            assert e.message == "Unable to create the tenant request"
        }
    }

    @Test
    void testTheTenantServiceExplanationReachesTheCaller() {
        SubscribedTenantController controller = controllerThatFailsWith(bodyFailure(400,
                '{"message":"Domain acme.com is already in use","_links":{"self":{"href":"/subscribedtenant/"}}}'))

        try {
            controller.requestTenant(new TenantRequestInput(name: "Acme", domain: "acme.com"))
            assert false
        } catch (HttpStatusException e) {
            assert e.status == HttpStatus.BAD_REQUEST
            assert e.message == "Domain acme.com is already in use"
        }
    }

    @Test
    void testAnEmbeddedErrorIsUsedWhenThereIsNoTopLevelMessage() {
        SubscribedTenantController controller = controllerThatFailsWith(bodyFailure(400,
                '{"_embedded":{"errors":[{"message":"A valid tenant domain is required"}]}}'))

        try {
            controller.requestTenant(new TenantRequestInput(name: "Acme", domain: "nope"))
            assert false
        } catch (HttpStatusException e) {
            assert e.message == "A valid tenant domain is required"
        }
    }

    @Test
    void testAnUnparseableBodyFallsBackToOurOwnWording() {
        SubscribedTenantController controller = controllerThatFailsWith(bodyFailure(400, "<html>nginx</html>"))

        try {
            controller.provision("req-1")
            assert false
        } catch (HttpStatusException e) {
            assert e.message == "Unable to provision the tenant"
        }
    }

    @Test
    void testAFailingTenantServiceIsNeverQuotedBackToTheCaller() {
        SubscribedTenantController controller = controllerThatFailsWith(bodyFailure(500,
                '{"message":"Internal Server Error"}'))

        try {
            controller.provision("req-1")
            assert false
        } catch (HttpStatusException e) {
            assert e.status == HttpStatus.BAD_GATEWAY
            assert e.message == "Unable to provision the tenant"
        }
    }

    @Test
    void testAnUnauthorizedUpstreamCallKeepsItsStatus() {
        SubscribedTenantController controller = controllerThatFailsWith(
                new InvalidRequestException(new RuntimeException("nope"), 401))

        try {
            controller.provision("req-1")
            assert false
        } catch (HttpStatusException e) {
            assert e.status == HttpStatus.UNAUTHORIZED
        }
    }

    @Test
    void testAnUnreachableTenantServiceIsReportedAsABadGateway() {
        SubscribedTenantController controller = controllerThatFailsWith(
                new InvalidRequestException(new RuntimeException("connection refused")))

        try {
            controller.createCheckoutSession("req-1")
            assert false
        } catch (HttpStatusException e) {
            assert e.status == HttpStatus.BAD_GATEWAY
        }
    }

    @Test
    void testAFailingTenantServiceIsNotBlamedOnTheHomepage() {
        SubscribedTenantController controller = controllerThatFailsWith(
                new InvalidRequestException(new RuntimeException("boom"), 500))

        try {
            controller.provision("req-1")
            assert false
        } catch (HttpStatusException e) {
            assert e.status == HttpStatus.BAD_GATEWAY
        }
    }

    @Test
    void testAFailedLookupDoesNotBecomeAServerError() {
        SubscribedTenantController controller = controllerThatFailsWith(
                new InvalidRequestException(new RuntimeException("nope"), 404))

        try {
            controller.getCurrentRequest()
            assert false
        } catch (HttpStatusException e) {
            assert e.status == HttpStatus.NOT_FOUND
        }
    }

    private static InvalidRequestException bodyFailure(int statusCode, String body) {
        return new InvalidRequestException(
                new HttpResponseBodyException(statusCode, "Bad Request", body), statusCode)
    }

    private SubscribedTenantController controllerThatFailsWith(InvalidRequestException failure) {
        SubscribedTenantController controller = new SubscribedTenantController()
        controller.secureHttpClient = [
                get : { String url -> throw failure },
                post: { String url, String body -> throw failure }
        ] as SecureHttpClient
        return controller
    }

    private SubscribedTenantController controllerWith(String response) {
        SubscribedTenantController controller = new SubscribedTenantController()
        controller.secureHttpClient = [
                get : { String url ->
                    gets << url
                    return response
                },
                post: { String url, String body ->
                    posts << [url: url, body: body]
                    return response
                }
        ] as SecureHttpClient
        return controller
    }
}
