package com.trevorism.gcloud.webapi.controller

import com.google.gson.Gson
import com.trevorism.gcloud.webapi.model.TenantRequestInput
import com.trevorism.https.SecureHttpClient
import com.trevorism.secure.Roles
import com.trevorism.secure.Secure
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.inject.Inject

@Controller("/api/tenant/request")
class TenantRequestController {

    public static final String BASE_URL = "https://tenant.auth.trevorism.com"

    @Inject
    private SecureHttpClient secureHttpClient
    private Gson gson = new Gson()

    @Tag(name = "Tenant Request Operations")
    @Operation(summary = "Request a new tenant **Secure")
    @Secure(Roles.USER)
    @Post(value = "/", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    Map requestTenant(@Body TenantRequestInput input) {
        String json = secureHttpClient.post("$BASE_URL/tenant/request/", gson.toJson(input))
        return gson.fromJson(json, Map)
    }

    @Tag(name = "Tenant Request Operations")
    @Operation(summary = "Get the current caller's tenant request **Secure")
    @Secure(Roles.USER)
    @Get(value = "/", produces = MediaType.APPLICATION_JSON)
    Map getCurrentRequest() {
        String json = secureHttpClient.get("$BASE_URL/tenant/request/me")
        return json ? gson.fromJson(json, Map) : [:]
    }

    @Tag(name = "Tenant Request Operations")
    @Operation(summary = "Create a subscription checkout session for a tenant request **Secure")
    @Secure(Roles.USER)
    @Get(value = "/{requestId}/session", produces = MediaType.APPLICATION_JSON)
    Map createCheckoutSession(String requestId) {
        String json = secureHttpClient.get("$BASE_URL/tenant/request/${requestId}/session")
        return gson.fromJson(json, Map)
    }

    @Tag(name = "Tenant Request Operations")
    @Operation(summary = "Provision the tenant once the subscription is active **Secure")
    @Secure(Roles.USER)
    @Post(value = "/{requestId}/provision", produces = MediaType.APPLICATION_JSON)
    Map provision(String requestId) {
        String json = secureHttpClient.post("$BASE_URL/tenant/request/${requestId}/provision", "{}")
        return gson.fromJson(json, Map)
    }
}
