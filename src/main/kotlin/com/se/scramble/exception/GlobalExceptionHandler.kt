package com.se.scramble.exception

import com.se.scramble.domain.RestAPIMessages
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.nio.charset.Charset

@ControllerAdvice
class GlobalExceptionHandler {
    val logger: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    private fun sendExceptionRestMessages(
        httpStatus: HttpStatus,
        message: String,
        errorCode: Int
    ): ResponseEntity<RestAPIMessages> {
        val restAPIMessages: RestAPIMessages = RestAPIMessages(
            httpStatus = httpStatus,
            message = message,
            data = null,
            errorCode = errorCode
        )
        val headers: HttpHeaders = HttpHeaders()
        headers.contentType = MediaType("application", "json", Charset.forName("UTF-8"))
        return ResponseEntity<RestAPIMessages>(restAPIMessages, headers, httpStatus)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<RestAPIMessages> {
        logger.error(e.message, e)
        return sendExceptionRestMessages(HttpStatus.INTERNAL_SERVER_ERROR, e.message!!, -1)
    }

    @ExceptionHandler(FailToLoginException::class)
    fun handleFailToLoginException(e: FailToLoginException): ResponseEntity<RestAPIMessages> {
        logger.error(e.message, e)
        return sendExceptionRestMessages(HttpStatus.UNAUTHORIZED, e.message!!, -2)
    }

    @ExceptionHandler(Exception::class)
    fun handleUnknownException(e: IllegalArgumentException): ResponseEntity<RestAPIMessages> {
        logger.error(e.message, e)
        return sendExceptionRestMessages(HttpStatus.NOT_IMPLEMENTED, e.message!!, -999)
    }
}