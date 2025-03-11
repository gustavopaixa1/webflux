package com.udemy.webflux.model.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class UserRequest(

    @field:Size(min = 3, max = 50)
    @field:NotBlank()
    val name: String,

    @field:Size(min = 3, max = 50)
    @field:NotBlank()
    @field:Email()
    val email: String,

    @field:Size(min = 3, max = 50)
    @field:NotBlank()
    val password: String
)

