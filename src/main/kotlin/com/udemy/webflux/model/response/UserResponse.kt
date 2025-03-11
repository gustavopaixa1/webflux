package com.udemy.webflux.model.response

data class UserResponse(
    var id: String?,
    var name: String,
    var email: String,
    var password: String
) {

}
