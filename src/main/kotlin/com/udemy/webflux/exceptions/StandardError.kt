package com.udemy.webflux.exceptions

import java.time.LocalDateTime

open class StandardError(
    val timestamp: LocalDateTime,
    val path: String,
    val status: Int,
    val error: String,
    val message: String
)