package com.trevorism.gcloud.webapi.controller

import com.google.gson.Gson
import com.trevorism.gcloud.webapi.model.User
import com.trevorism.gcloud.webapi.service.MultipartHttpClient
import com.trevorism.gcloud.webapi.service.UserSessionService
import com.trevorism.https.SecureHttpClient
import com.trevorism.secure.Roles
import com.trevorism.secure.Secure
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Status
import io.micronaut.http.exceptions.HttpStatusException
import io.micronaut.http.multipart.CompletedFileUpload
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.inject.Inject
import org.apache.hc.client5.http.classic.methods.HttpPost
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.core5.http.ContentType
import org.apache.hc.core5.http.HttpEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller("/api/image")
class ImageController {

    private static final Logger log = LoggerFactory.getLogger(ImageController.class.getName())

    @Inject
    private SecureHttpClient secureHttpClient

    @Inject
    private UserSessionService userSessionService

    @Tag(name = "Image Operations")
    @Operation(summary = "Lookup Image")
    @Get(value = "{username}/profile", produces = MediaType.APPLICATION_OCTET_STREAM)
    byte[] get(String username) {
        String list = secureHttpClient.get("https://bucket.data.trevorism.com/object")
        def objects = new Gson().fromJson(list, List.class)
        String restOfPath = objects.find { String path -> path.contains("${username}/profile") }
        if(!restOfPath) {
            throw new HttpStatusException(HttpStatus.NOT_FOUND, "Image not found")
        }
        String url = "https://bucket.data.trevorism.com/object/${restOfPath}"
        return secureHttpClient.get(url).bytes
    }

    @Tag(name = "Image Operations")
    @Operation(summary = "Lookup Image")
    @Get(value = "{username}/profile/{imageFileName}", produces = MediaType.APPLICATION_OCTET_STREAM)
    byte[] get(String username, String imageFileName) {
        String url = "https://bucket.data.trevorism.com/object/${username}/profile/${imageFileName}"
        return secureHttpClient.get(url).bytes
    }

    @Tag(name = "Image Operations")
    @Operation(summary = "Upload image at {path} **Secure")
    @Status(HttpStatus.CREATED)
    @Post(value = "/", consumes = MediaType.MULTIPART_FORM_DATA, produces = MediaType.TEXT_PLAIN)
    @Secure(value = Roles.USER, allowInternal = true)
    String upload(CompletedFileUpload file) {
        try {
            User user = userSessionService.getUserFromToken()
            String extension = file.contentType.get().subtype
            String url = "https://bucket.data.trevorism.com/object/${user.username}/profile/profile.${extension}"
            byte[] dataToSend = file.getBytes()
            String result = new MultipartHttpClient(secureHttpClient.obtainTokenStrategy).post(url, dataToSend, file.filename)
            return result
        } catch (Exception e) {
            log.error("Unable to create image", e)
            throw new HttpStatusException(HttpStatus.BAD_REQUEST, "Unable to create image")
        }
    }

}
