package com.udemy.webflux.controller

import com.udemy.webflux.model.request.UserRequest
import com.udemy.webflux.model.response.UserResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserController {

    @PostMapping
    fun save(@RequestBody request: UserRequest): ResponseEntity<Mono<Void>>

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): ResponseEntity<Mono<UserResponse>>

    @GetMapping
    fun findAll(): ResponseEntity<Flux<UserResponse>>

    @PatchMapping("/id")
    fun update(@PathVariable id: String, @RequestBody request: UserRequest): ResponseEntity<Mono<UserResponse>>

    @DeleteMapping("/id")
    fun delete(@PathVariable id: String): ResponseEntity<Mono<Void>>
}