package com.se.scramble.domain

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus


data class RestAPIMessages(
    val httpStatus: HttpStatus,
    val message: String,
    val data: Any?,
    val errorCode: Int
) {
    public fun convertToJson(): String =
        ObjectMapper().writeValueAsString(this)

}