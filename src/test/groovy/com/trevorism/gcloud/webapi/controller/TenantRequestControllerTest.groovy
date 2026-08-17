package com.trevorism.gcloud.webapi.controller

import com.google.gson.Gson
import com.trevorism.gcloud.webapi.model.TenantRequestInput
import com.trevorism.https.SecureHttpClient
import org.junit.jupiter.api.Test

class TenantRequestControllerTest {

    private Gson gson = new Gson()
    private List<Map> posts = []
    private List<String> gets = []

    @Test
    void testRequestTenantForwardsTheInputToTheTenantService() {
        TenantRequestController controller = controllerWith('{"id":"req-1","status":"PENDING_PAYMENT"}')

        Map result = controller.requestTenant(new TenantRequestInput(name: "Acme", domain: "acme.com"))

        assert result.id == "req-1"
        assert posts[0].url == "https://tenant.auth.trevorism.com/tenant/request/"
        Map body = gson.fromJson(posts[0].body as String, Map)
        assert body.name == "Acme"
        assert body.domain == "acme.com"
    }

    @Test
    void testGetCurrentRequestReadsTheCallersRequest() {
        TenantRequestController controller = controllerWith('{"id":"req-1","status":"PROVISIONED"}')

        assert controller.getCurrentRequest().status == "PROVISIONED"
        assert gets[0] == "https://tenant.auth.trevorism.com/tenant/request/me"
    }

    @Test
    void testGetCurrentRequestReturnsAnEmptyMapWhenThereIsNoContent() {
        TenantRequestController controller = controllerWith("")

        assert controller.getCurrentRequest() == [:]
    }

    @Test
    void testCreateCheckoutSessionReturnsTheStripeSession() {
        TenantRequestController controller = controllerWith('{"id":"cs_1","url":"https://checkout.stripe.com/c/pay/cs_1"}')

        Map session = controller.createCheckoutSession("req-1")

        assert session.url == "https://checkout.stripe.com/c/pay/cs_1"
        assert gets[0] == "https://tenant.auth.trevorism.com/tenant/request/req-1/session"
    }

    @Test
    void testProvisionPostsToTheTenantService() {
        TenantRequestController controller = controllerWith('{"id":"req-1","status":"PROVISIONED","tenantGuid":"guid-1"}')

        Map result = controller.provision("req-1")

        assert result.tenantGuid == "guid-1"
        assert posts[0].url == "https://tenant.auth.trevorism.com/tenant/request/req-1/provision"
    }

    private TenantRequestController controllerWith(String response) {
        TenantRequestController controller = new TenantRequestController()
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
