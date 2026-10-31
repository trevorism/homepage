package com.trevorism.gcloud.webapi.service

import io.micronaut.core.io.ResourceResolver
import io.micronaut.http.HttpStatus
import org.junit.jupiter.api.Test

class PageResolverTest {

    private final PageResolver pageResolver = new PageResolver(new ResourceResolver(), "classpath:test-pages/")

    @Test
    void testHomeResolvesToThePrerenderedHomePage() {
        assertPage("/", HttpStatus.OK, "index.html")
    }

    @Test
    void testPagesResolveToTheirPrerenderedDocuments() {
        assertPage("/docs", HttpStatus.OK, "docs.html")
        assertPage("/articles/production", HttpStatus.OK, "articles/production.html")
    }

    @Test
    void testAppRoutesResolveToTheAppShell() {
        ["/account", "/change", "/change/20213392-c6bf-49c8-9cab-90b991b4a01f"].each {
            assertPage(it, HttpStatus.OK, "_app.html")
        }
    }

    @Test
    void testUnknownPathsResolveToTheNotFoundPage() {
        ["/no-such-page", "/articles/missing", "/change/guid/extra", "/Docs", "/docs/", "/_app", "/_404", "/../application"].each {
            assertPage(it, HttpStatus.NOT_FOUND, "_404.html")
        }
    }

    @Test
    void testMissingPagesLeaveNothingToServe() {
        PageResolver withoutPages = new PageResolver(new ResourceResolver(), "classpath:missing-pages/")
        assert !withoutPages.resolve("/docs").document
        assert !withoutPages.resolve("/account").document
    }

    @Test
    void testRetiredPathsRedirectPermanentlyToTheirReplacement() {
        def page = pageResolver.resolve("/contact")
        assert page.status == HttpStatus.MOVED_PERMANENTLY
        assert page.redirectTo == "/assessment"
        assert !page.document
    }

    @Test
    void testOptionalRouteParametersMatchWithAndWithoutAValue() {
        def pattern = PageResolver.toPattern("/change/:guid?")
        assert "/change" ==~ pattern
        assert "/change/abc" ==~ pattern
        assert !("/change/abc/def" ==~ pattern)
    }

    private void assertPage(String path, HttpStatus status, String file) {
        def page = pageResolver.resolve(path)
        assert page.status == status, path
        assert page.document.path.endsWith("/test-pages/${file}"), path
    }
}
