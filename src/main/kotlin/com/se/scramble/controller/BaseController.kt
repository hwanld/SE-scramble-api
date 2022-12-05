package com.se.scramble.controller

import com.se.scramble.config.WebMvcConfig
import com.se.scramble.domain.RestAPIMessages
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.nio.charset.Charset
import javax.persistence.MappedSuperclass

@RestController
class BaseController {
    fun sendResponseHttpByJson(message: String, data: Any?): ResponseEntity<RestAPIMessages> {
        val restAPIMessages = RestAPIMessages(
            httpStatus = HttpStatus.OK,
            message = message,
            data = data,
            errorCode = 0
        )
        val headers: HttpHeaders = HttpHeaders()
        headers.contentType = MediaType("application", "json", Charset.forName("UTF-8"))
        return ResponseEntity<RestAPIMessages>(restAPIMessages, headers, HttpStatus.OK)
    }
}