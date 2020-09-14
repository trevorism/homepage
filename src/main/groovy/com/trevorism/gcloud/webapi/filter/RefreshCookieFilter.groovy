package com.trevorism.gcloud.webapi.filter

import com.trevorism.http.headers.HeadersHttpClient
import com.trevorism.http.headers.HeadersJsonHttpClient
import com.trevorism.http.util.ResponseUtils
import org.apache.http.client.methods.CloseableHttpResponse

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.ws.rs.client.ClientRequestContext
import javax.ws.rs.client.ClientResponseContext
import javax.ws.rs.client.ClientResponseFilter
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.container.ContainerResponseFilter
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.NewCookie
import javax.ws.rs.ext.Provider

class RefreshCookieFilter implements ContainerResponseFilter {

    private HeadersHttpClient headersHttpClient = new HeadersJsonHttpClient()

    @Context HttpServletRequest request
    @Context HttpServletResponse response

    @Override
    void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        String bearerString = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)

        //You can get the token without bearer from the session, for functions that are not secured...
        //println request.getSession().getAttribute("session")

        if(bearerString && bearerString.toLowerCase().startsWith("bearer ") && !bearerString.endsWith("null")){
            CloseableHttpResponse result = headersHttpClient.post("https://auth.trevorism.com/token/refresh","{}",["Authorization":bearerString])
            String token = ResponseUtils.getEntity(result)

            Cookie cookie = new Cookie("session",token)
            cookie.setPath("/")
            cookie.setMaxAge(15*60)
            response.addCookie(cookie)
        }
    }
}
