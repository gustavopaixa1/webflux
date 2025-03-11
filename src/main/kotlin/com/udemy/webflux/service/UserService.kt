package com.udemy.webflux.service

import com.udemy.webflux.entity.User
import com.udemy.webflux.mapper.UserMapper
import com.udemy.webflux.model.request.UserRequest
import com.udemy.webflux.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserService(
    private val userRepository: UserRepository,
    private val mapper: UserMapper
) {

    fun save(request: UserRequest): Mono<User> {
        return userRepository.save(mapper.toEntity(request))
    }

    fun findById(id: String): Mono<User> {
        return userRepository.findById(id)
    }
}