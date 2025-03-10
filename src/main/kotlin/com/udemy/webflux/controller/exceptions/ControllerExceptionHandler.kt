package com.udemy.webflux.controller.exceptions

import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ServerWebExchange
import java.time.LocalDateTime.now

@ControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(DuplicateKeyException::class)
    fun duplicateKey(
        ex: DuplicateKeyException, exchange: ServerWebExchange
    ): ResponseEntity<StandardError> {
        println(exchange.request.uri.path)
        println(exchange)
        return ResponseEntity.badRequest()
            .body(
                StandardError(
                    timestamp = now(),
                    path = exchange.request.uri.path,
                    status = BAD_REQUEST.value(),
                    error = BAD_REQUEST.reasonPhrase,
                    message = verifyDupKey(ex.message)
                )
            )
    }


    private fun verifyDupKey(message: String?): String {
        if (message!!.contains("email dup key")) {
            return "Email already registered";
        }
        return "Dup key exception"
    }
}