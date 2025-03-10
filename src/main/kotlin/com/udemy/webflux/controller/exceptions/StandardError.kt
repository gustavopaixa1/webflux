package com.udemy.webflux.controller.exceptions

import java.time.LocalDateTime

data class StandardError(
    val timestamp: LocalDateTime,
    val path: String,
    val status: Int,
    val error: String,
    val message: String
)