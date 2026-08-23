package com.trevorism.gcloud.webapi.controller

import com.trevorism.gcloud.webapi.model.Tenant
import com.trevorism.http.util.InvalidRequestException
import com.trevorism.https.SecureHttpClient
import org.junit.jupiter.api.Test

class TenantControllerTest {

    private List<String> gets = []

    @Test
    void testGetCurrentTenantReadsTheCallersTenant() {
        TenantController controller = controllerWith('{"id":"1","name":"Acme","domain":"acme.com","guid":"guid-1","billingMode":"subscription","status":"active"}')

        Tenant tenant = controller.getCurrentTenant()

        assert tenant.id == "1"
        assert tenant.name == "Acme"
        assert tenant.domain == "acme.com"
        assert tenant.guid == "guid-1"
        assert tenant.billingMode == "subscription"
        assert tenant.status == "active"
        assert gets[0] == "https://tenant.auth.trevorism.com/tenant/me"
    }

    @Test
    void testGetCurrentTenantReturnsANullTenantWhenTheCallerHasNoTenant() {
        assert Tenant.isNullTenant(controllerWith("").getCurrentTenant())
    }

    @Test
    void testGetCurrentTenantReturnsANullTenantWhenTheTenantServiceRefuses() {
        TenantController controller = new TenantController()
        controller.secureHttpClient = [get: { url ->
            throw new InvalidRequestException(new RuntimeException("Not Found"), 404)
        }] as SecureHttpClient

        assert Tenant.isNullTenant(controller.getCurrentTenant())
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
