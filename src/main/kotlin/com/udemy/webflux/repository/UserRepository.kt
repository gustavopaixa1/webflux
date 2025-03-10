package com.udemy.webflux.repository

import com.udemy.webflux.entity.User
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class UserRepository(
    private val mongoTemplate: ReactiveMongoTemplate
) {

    public fun save(user: User): Mono<User>{
        return mongoTemplate.save(user)
    }
}