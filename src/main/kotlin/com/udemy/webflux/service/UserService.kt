package com.udemy.webflux.service

import com.udemy.webflux.entity.User
import com.udemy.webflux.exceptions.ObjectNotFoundException
import com.udemy.webflux.mapper.UserMapper
import com.udemy.webflux.model.request.UserRequest
import com.udemy.webflux.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
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
        return userRepository.findById(id).switchIfEmpty(Mono.error(
            ObjectNotFoundException("User not found with id: $id, type: ${User::class.simpleName}")
        ))
    }

    fun findAll(): Flux<User>{
        return userRepository.findAll().switchIfEmpty(
            Mono.error(
                ObjectNotFoundException("No users found")
            )
        )
    }

    fun update(id: String, request: UserRequest): Mono<User> {
        return findById(id)
            .map { user -> mapper.toEntity(request, user) }
            .flatMap { updatedUser -> userRepository.save(updatedUser) }
    }

    fun delete(id: String): Mono<User>{
        return findById(id)
            .flatMap { user -> userRepository.delete(user).thenReturn(user) }
    }

}