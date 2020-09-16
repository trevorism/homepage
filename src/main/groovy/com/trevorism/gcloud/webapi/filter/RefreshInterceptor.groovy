package com.trevorism.gcloud.webapi.filter

import com.trevorism.secure.Secure

import javax.ws.rs.container.DynamicFeature
import javax.ws.rs.container.ResourceInfo
import javax.ws.rs.core.FeatureContext
import javax.ws.rs.ext.Provider

@Provider
class RefreshInterceptor implements DynamicFeature{
    @Override
    void configure(ResourceInfo resourceInfo, FeatureContext context) {
        if (resourceInfo.getResourceMethod().getAnnotation(Secure.class) == null)
            return

        context.register(RefreshCookieFilter.class)
        context.register(TestFilter.class)
    }
}

