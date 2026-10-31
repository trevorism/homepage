package com.trevorism.gcloud.webapi.controller

import com.trevorism.gcloud.webapi.model.ResolvedPage
import com.trevorism.gcloud.webapi.service.PageResolver
import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.server.types.files.StreamedFile
import jakarta.inject.Inject
import io.micronaut.http.annotation.Error

@Controller("/notfound")
class NotFoundRouteController {

    @Inject
    PageResolver pageResolver

    @Error(status = HttpStatus.NOT_FOUND, global = true)
    HttpResponse forward(HttpRequest request) {
        if (!isPageRequest(request)) {
            return HttpResponse.notFound()
        }
        ResolvedPage page = pageResolver.resolve(request.path)
        if (!page.document) {
            return HttpResponse.notFound()
        }
        return HttpResponse.status(page.status).body(new StreamedFile(page.document))
    }

    private static boolean isPageRequest(HttpRequest<?> request) {
        if (!(request.method in [HttpMethod.GET, HttpMethod.HEAD])) {
            return false
        }
        String path = request.path
        if (path.startsWith("/assets/") || path.startsWith("/api/")) {
            return false
        }
        int lastSlash = path.lastIndexOf('/')
        return !path.substring(lastSlash + 1).contains('.')
    }
}
