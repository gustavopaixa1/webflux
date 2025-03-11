package com.udemy.webflux.controller.impl

import com.udemy.webflux.controller.UserController
import com.udemy.webflux.mapper.UserMapper
import com.udemy.webflux.model.request.UserRequest
import com.udemy.webflux.model.response.UserResponse
import com.udemy.webflux.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/users")
class UserControllerImpl(
    private val userService: UserService,
    private val userMapper: UserMapper
) : UserController {

    private val TODO: String = "Not Yet Implemented"

    @PostMapping
    override fun save(@Valid @RequestBody request: UserRequest): ResponseEntity<Mono<Void>> {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(request).then())
    }

    @GetMapping("/{id}")
    override fun findById(@PathVariable id: String): ResponseEntity<Mono<UserResponse>> {
        return ResponseEntity.ok(userService.findById(id).map { userMapper.toResponse(it) })
    }

    override fun findAll(): ResponseEntity<Flux<UserResponse>> {
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