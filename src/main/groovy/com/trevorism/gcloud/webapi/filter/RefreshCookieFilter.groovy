package com.trevorism.gcloud.webapi.filter

import com.trevorism.http.headers.HeadersHttpClient
import com.trevorism.http.headers.HeadersJsonHttpClient
import com.trevorism.http.util.ResponseUtils
import org.apache.http.client.methods.CloseableHttpResponse

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.container.ContainerResponseFilter
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders

class RefreshCookieFilter implements ContainerResponseFilter {

    private HeadersHttpClient headersHttpClient = new HeadersJsonHttpClient()

    @Context
    HttpServletRequest request
    @Context
    HttpServletResponse response

    @Override
    void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        String bearerString = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)

        if (bearerString && bearerString.toLowerCase().startsWith("bearer ") && !bearerString.endsWith("null")) {
            CloseableHttpResponse result = headersHttpClient.post("https://auth.trevorism.com/token/refresh", "{}", ["Authorization": bearerString])
            String token = ResponseUtils.getEntity(result)

            Cookie cookie = new Cookie("session", token)
            cookie.setPath("/")
            cookie.setMaxAge(15 * 60)
            cookie.setSecure(getSecureCookieValue())
            response.addCookie(cookie)
        }
    }

    private static boolean getSecureCookieValue() {
        if (System.getenv("LOCALHOST_COOKIES"))
            return false
        return true
    }
}
