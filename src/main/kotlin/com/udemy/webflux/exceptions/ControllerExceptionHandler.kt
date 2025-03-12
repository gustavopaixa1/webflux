package com.udemy.webflux.exceptions

import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.time.LocalDateTime.now

@ControllerAdvice
class ControllerExceptionHandler {

    val emailAlreadyRegistered: String = "Email already registered"
    val validationError: String = "ValidationError"
    val validationErrorMessage: String = "Error on validation"


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

    @ExceptionHandler(WebExchangeBindException::class)
    fun validationError(
        ex: WebExchangeBindException, exchange: ServerWebExchange
    ): ResponseEntity<Mono<ValidationError>> {
        val error = ValidationError(
            timestamp = now(),
            path = exchange.request.uri.path,
            status = BAD_REQUEST.value(),
            error = validationError,
            message = validationErrorMessage
        )

        for (x in ex.bindingResult.fieldErrors){
            error.addError(x.field, x.defaultMessage.toString())
        }

        return ResponseEntity.status(BAD_REQUEST).body(Mono.just(error))
    }

    @ExceptionHandler(ObjectNotFoundException::class)
    fun objectNotFound(ex: ObjectNotFoundException, exchange: ServerWebExchange): ResponseEntity<StandardError> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(
                StandardError(
                    timestamp = now(),
                    path = exchange.request.uri.path,
                    status = HttpStatus.NOT_FOUND.value(),
                    error = HttpStatus.NOT_FOUND.reasonPhrase,
                    message = ex.message.toString()
                )
            )
    }


    private fun verifyDupKey(message: String?): String {
        if (message!!.contains("email dup key")) {
            return emailAlreadyRegistered
        }
        return "Dup key exception"
    }
}