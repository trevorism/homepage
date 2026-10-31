package com.trevorism.gcloud.webapi.service

import com.google.gson.Gson
import com.trevorism.gcloud.webapi.model.ResolvedPage
import io.micronaut.core.io.ResourceResolver
import io.micronaut.http.HttpStatus
import jakarta.inject.Inject
import jakarta.inject.Singleton

import java.util.regex.Pattern

@Singleton
class PageResolver {

    static final String PAGES_ROOT = "classpath:pages/"
    static final String HOME_PAGE = "index.html"
    static final String APP_SHELL = "_app.html"
    static final String NOT_FOUND_PAGE = "_404.html"
    static final String APP_ROUTES = "_app-routes.json"

    private static final Pattern PRERENDERED_PATH = ~/^(\/[a-z0-9]+(-[a-z0-9]+)*)+$/

    private final ResourceResolver resourceResolver
    private final String pagesRoot
    private final List<Pattern> appRoutePatterns

    @Inject
    PageResolver(ResourceResolver resourceResolver) {
        this(resourceResolver, PAGES_ROOT)
    }

    PageResolver(ResourceResolver resourceResolver, String pagesRoot) {
        this.resourceResolver = resourceResolver
        this.pagesRoot = pagesRoot
        this.appRoutePatterns = loadAppRoutePatterns()
    }

    ResolvedPage resolve(String path) {
        URL prerendered = find(prerenderedFile(path))
        if (prerendered) {
            return new ResolvedPage(HttpStatus.OK, prerendered)
        }
        if (appRoutePatterns.any { path ==~ it }) {
            return new ResolvedPage(HttpStatus.OK, find(APP_SHELL))
        }
        return new ResolvedPage(HttpStatus.NOT_FOUND, find(NOT_FOUND_PAGE))
    }

    static Pattern toPattern(String routePath) {
        String regex = routePath
                .replaceAll(/\/:\w+\?/, '(?:/[^/]+)?')
                .replaceAll(/\/:\w+/, '/[^/]+')
        return Pattern.compile("^${regex}\$")
    }

    private static String prerenderedFile(String path) {
        if (path == "/") {
            return HOME_PAGE
        }
        return path ==~ PRERENDERED_PATH ? "${path.substring(1)}.html" : null
    }

    private URL find(String file) {
        return file ? resourceResolver.getResource(pagesRoot + file).orElse(null) : null
    }

    private List<Pattern> loadAppRoutePatterns() {
        URL manifest = find(APP_ROUTES)
        if (!manifest) {
            return []
        }
        List<String> routePaths = new Gson().fromJson(manifest.text, List)
        return routePaths.collect { toPattern(it) }
    }
}
