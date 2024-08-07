package com.kkpa.jbh.controllers

import com.kkpa.jbh.exceptions.ConstraintViolationsException
import com.kkpa.jbh.payload.ErrorMessageResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice
class GlobalErrorHandler {

    private val log = LoggerFactory.getLogger(GlobalErrorHandler::class.java)

    @ExceptionHandler(ConstraintViolationsException::class)
    @ResponseBody
    fun constraintViolationsHandler(
        ex: ConstraintViolationsException,
        request: WebRequest
    ): ResponseEntity<Any> {

        val messageResponse = ex.errorsResponse.errors.map {
            it.message
        }.joinToString(separator = " - ")

        return ResponseEntity.badRequest().body(ErrorMessageResponse(messageResponse))
    }

    @ExceptionHandler(Throwable::class)
    fun throwableHandler(ex: Throwable): ResponseEntity<ErrorMessageResponse> {
        log.error("An exception not caught: ${ex.message} ${ex.stackTrace}")

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorMessageResponse("Working to fix this error. Be patience!"))
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun badCredentialsExceptionHandler(ex: Throwable): ResponseEntity<ErrorMessageResponse> {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorMessageResponse(ex.message!!))
    }
}