package com.udemy.webflux.repository

import com.udemy.webflux.entity.User
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class UserRepository(
    private val mongoTemplate: ReactiveMongoTemplate
) {

    public fun save(user: User): Mono<User>{
        return mongoTemplate.save(user)
    }

    public fun findById(id: String): Mono<User>{
        return mongoTemplate.findById(id, User::class.java)
    }

    public fun findAll(): Flux<User> {
        return mongoTemplate.findAll(User::class.java)
    }

    public fun delete(user: User): Mono<Void>{
        return mongoTemplate.remove(user).then()
    }

}