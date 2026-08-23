package com.trevorism.gcloud.webapi.controller

import com.trevorism.http.util.InvalidRequestException
import com.trevorism.https.SecureHttpClient
import org.junit.jupiter.api.Test

class TenantControllerTest {

    private List<String> gets = []

    @Test
    void testGetCurrentTenantReadsTheCallersTenant() {
        TenantController controller = controllerWith('{"id":"1","name":"Acme","domain":"acme.com","guid":"guid-1"}')

        Map tenant = controller.getCurrentTenant()

        assert tenant.name == "Acme"
        assert tenant.guid == "guid-1"
        assert gets[0] == "https://tenant.auth.trevorism.com/tenant/me"
    }

    @Test
    void testGetCurrentTenantReturnsAnEmptyMapWhenTheCallerHasNoTenant() {
        assert controllerWith("").getCurrentTenant() == [:]
    }

    @Test
    void testGetCurrentTenantReturnsAnEmptyMapWhenTheTenantServiceRefuses() {
        TenantController controller = new TenantController()
        controller.secureHttpClient = [get: { url ->
            throw new InvalidRequestException(new RuntimeException("Not Found"), 404)
        }] as SecureHttpClient

        assert controller.getCurrentTenant() == [:]
    }

    private TenantController controllerWith(String response) {
        TenantController controller = new TenantController()
        controller.secureHttpClient = [get: { url ->
            gets << url
            return response
        }] as SecureHttpClient
        return controller
    }
}
