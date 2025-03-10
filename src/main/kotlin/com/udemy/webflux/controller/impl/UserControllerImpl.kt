package com.udemy.webflux.controller.impl

import com.udemy.webflux.controller.UserController
import com.udemy.webflux.model.request.UserRequest
import com.udemy.webflux.model.response.UserResponse
import com.udemy.webflux.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/users")
class UserControllerImpl(
    private val userService: UserService
) : UserController {

    private val TODO: String = "Not Yet Implemented"

    @PostMapping
    override fun save(@RequestBody request: UserRequest): ResponseEntity<Mono<Void>> {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(request).then())
    }

    override fun find(id: String): ResponseEntity<Mono<UserResponse>> {
        TODO(TODO)
    }

    override fun finAll(): ResponseEntity<Flux<UserResponse>> {
        TODO(TODO)
    }

    override fun update(
        id: String,
        request: UserRequest
    ): ResponseEntity<Mono<Unit>> {
        TODO(TODO)
    }

    override fun delete(id: String): ResponseEntity<Mono<Void>> {
        TODO(TODO)
    }
}