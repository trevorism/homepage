package com.trevorism.gcloud.webapi.controller

import com.google.gson.Gson
import com.trevorism.gcloud.webapi.model.TenantRequestInput
import com.trevorism.http.util.InvalidRequestException
import com.trevorism.https.SecureHttpClient
import com.trevorism.secure.Roles
import com.trevorism.secure.Secure
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.exceptions.HttpStatusException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.inject.Inject

@Controller("/api/subscribedtenant")
class SubscribedTenantController {

    public static final String BASE_URL = "https://tenant.auth.trevorism.com"

    @Inject
    private SecureHttpClient secureHttpClient
    private Gson gson = new Gson()

    @Tag(name = "Tenant Request Operations")
    @Operation(summary = "Request a new tenant **Secure")
    @Secure(Roles.USER)
    @Post(value = "/", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    Map requestTenant(@Body TenantRequestInput input) {
        return proxy("Unable to create the tenant request") {
            gson.fromJson(secureHttpClient.post("$BASE_URL/subscribedtenant/", gson.toJson(input)), Map)
        }
    }

    @Tag(name = "Tenant Request Operations")
    @Operation(summary = "Get the current caller's tenant request **Secure")
    @Secure(Roles.USER)
    @Get(value = "/", produces = MediaType.APPLICATION_JSON)
    Map getCurrentRequest() {
        return proxy("Unable to read the tenant request") {
            String json = secureHttpClient.get("$BASE_URL/subscribedtenant/me")
            return json ? gson.fromJson(json, Map) : [:]
        }
    }

    @Tag(name = "Tenant Request Operations")
    @Operation(summary = "Get the subscription backing the current caller **Secure")
    @Secure(Roles.USER)
    @Get(value = "/subscription", produces = MediaType.APPLICATION_JSON)
    Map getSubscription() {
        return proxy("Unable to read the subscription") {
            String json = secureHttpClient.get("$BASE_URL/subscribedtenant/subscription")
            return json ? gson.fromJson(json, Map) : [:]
        }
    }

    @Tag(name = "Tenant Request Operations")
    @Operation(summary = "Create a subscription checkout session for a tenant request **Secure")
    @Secure(Roles.USER)
    @Post(value = "/{requestId}/session", produces = MediaType.APPLICATION_JSON)
    Map createCheckoutSession(String requestId) {
        return proxy("Unable to start a checkout session") {
            gson.fromJson(secureHttpClient.post("$BASE_URL/subscribedtenant/${requestId}/session", "{}"), Map)
        }
    }

    @Tag(name = "Tenant Request Operations")
    @Operation(summary = "Provision the tenant once the subscription is active **Secure")
    @Secure(Roles.USER)
    @Post(value = "/{requestId}/tenant", produces = MediaType.APPLICATION_JSON)
    Map provision(String requestId) {
        return proxy("Unable to provision the tenant") {
            gson.fromJson(secureHttpClient.post("$BASE_URL/subscribedtenant/${requestId}/tenant", "{}"), Map)
        }
    }

    private Map proxy(String failureMessage, Closure<Map> closure) {
        try {
            return closure.call()
        } catch (InvalidRequestException e) {
            HttpStatus status = statusFor(e.statusCode)
            String message = status.code < 500 ? explanationFrom(e) : null
            throw new HttpStatusException(status, message ?: failureMessage)
        }
    }

    private String explanationFrom(InvalidRequestException e) {
        if (!e.responseBody) {
            return null
        }
        try {
            Map body = gson.fromJson(e.responseBody, Map)
            return body?.message ?: body?._embedded?.errors?.getAt(0)?.message
        } catch (Exception ignored) {
            return null
        }
    }

    private static HttpStatus statusFor(int statusCode) {
        if (statusCode < 400 || statusCode >= 500) {
            return HttpStatus.BAD_GATEWAY
        }
        try {
            return HttpStatus.valueOf(statusCode)
        } catch (IllegalArgumentException ignored) {
            return HttpStatus.BAD_REQUEST
        }
    }
}
