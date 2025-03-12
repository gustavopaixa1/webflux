package com.udemy.webflux.controller.impl

import com.udemy.webflux.controller.UserController
import com.udemy.webflux.entity.User
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
    override fun save(@Valid @RequestBody request: UserRequest): ResponseEntity<Mono<UserResponse>> {
        return ResponseEntity.ok(userService.save(request).map { userMapper.toResponse(it) })
    }

    @GetMapping("/{id}")
    override fun findById(@PathVariable id: String): ResponseEntity<Mono<UserResponse>> {
        return ResponseEntity.ok(userService.findById(id).map { userMapper.toResponse(it) })
    }

    @GetMapping
    override fun findAll(): ResponseEntity<Flux<UserResponse>> {
        return ResponseEntity.ok(userService.findAll().map { userMapper.toResponse(it) })
    }

    @PatchMapping("/{id}")
    override fun update(
        @PathVariable id: String,
        @RequestBody request: UserRequest
    ): ResponseEntity<Mono<UserResponse>> {
        return ResponseEntity.ok(userService.update(id, request).map { userMapper.toResponse(it) })
    }

    @DeleteMapping("/{id}")
    override fun delete(@PathVariable id: String): ResponseEntity<Mono<UserResponse>> {
        return ResponseEntity.ok(userService.delete(id).map { userMapper.toResponse(it) })
    }
}