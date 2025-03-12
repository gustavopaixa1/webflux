package com.udemy.webflux

import com.udemy.webflux.entity.User
import com.udemy.webflux.model.request.UserRequest
import com.udemy.webflux.model.response.UserResponse

class Builders {
    public fun userRequestBuilder(
        name: String = "gustavo",
        email: String = "gus@teste",
        password: String = "123"
    ) = UserRequest(name, email, password)

    public fun userBuilder(
        id: String = "123",
        name: String = "gustavo",
        email: String = "gus@teste",
        password: String = "123"
    ) = User(id, name, email, password)

    public fun userResponseBuilder(
        id: String = "123",
        name: String = "gustavo",
        email: String = "gus@teste",
        password: String = "123"
    ) = UserResponse(id, name, email, password)
}