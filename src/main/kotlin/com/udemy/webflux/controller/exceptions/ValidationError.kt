package com.udemy.webflux.controller.exceptions

import java.io.Serializable
import java.time.LocalDateTime

class ValidationError(
    timestamp: LocalDateTime,
    path: String,
    status: Int,
    error: String,
    message: String
) : StandardError(timestamp, path, status, error, message), Serializable {

    val errors: MutableList<FieldError> = mutableListOf()

    fun addError(fieldName: String, message: String) {
        this.errors.add(FieldError(fieldName, message))
    }

    data class FieldError(
        val fieldName: String,
        val message: String
    )
}