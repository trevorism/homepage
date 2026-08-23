package com.trevorism.gcloud.webapi.controller

import com.google.gson.Gson
import com.trevorism.gcloud.webapi.model.Tenant
import com.trevorism.https.SecureHttpClient
import com.trevorism.secure.Roles
import com.trevorism.secure.Secure
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.inject.Inject

@Controller("/api/tenant")
class TenantController {

    public static final String BASE_URL = "https://tenant.auth.trevorism.com"

    @Inject
    private SecureHttpClient secureHttpClient
    private Gson gson = new Gson()

    @Tag(name = "Tenant Operations")
    @Operation(summary = "Get the tenant of the current caller **Secure")
    @Secure(Roles.USER)
    @Get(value = "/", produces = MediaType.APPLICATION_JSON)
    Tenant getCurrentTenant() {
        try {
            String json = secureHttpClient.get("$BASE_URL/tenant/me")
            return json ? gson.fromJson(json, Tenant) : Tenant.NULL_TENANT
        } catch (Exception ignored) {
            return Tenant.NULL_TENANT
        }
    }
}
