package com.udemy.webflux.controller.impl

import com.udemy.webflux.Builders
import com.udemy.webflux.mapper.UserMapper
import com.udemy.webflux.model.response.UserResponse
import com.udemy.webflux.service.UserService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@WebFluxTest(UserControllerImpl::class)
class UserControllerImplTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var userService: UserService

    @MockBean
    private lateinit var userMapper: UserMapper

    private val builder = Builders()

    private val userRequest = builder.userRequestBuilder()
    private val userResponse = builder.userResponseBuilder()
    private val user = builder.userBuilder()

    @Test
    fun `should save user successfully`() {
        Mockito.`when`(userService.save(userRequest)).thenReturn(Mono.just(user))
        Mockito.`when`(userMapper.toResponse(user)).thenReturn(userResponse)

        webTestClient.post()
            .uri("/users")
            .bodyValue(userRequest)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.name").isEqualTo(userResponse.name)
            .jsonPath("$.email").isEqualTo(userResponse.email)
    }

    @Test
    fun `Should not save user with invalid email`() {
        val userRequest = builder.userRequestBuilder(email = "invalid-email")
        val errorMessage = "Error on validation"

        webTestClient.post()
            .uri("/users")
            .bodyValue(userRequest)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.message").isEqualTo(errorMessage)
            .jsonPath("$.errors[0].message").isEqualTo("deve ser um endereço de e-mail bem formado")
    }

    @Test
    fun `should find user by id`() {
        Mockito.`when`(userService.findById("1")).thenReturn(Mono.just(user))
        Mockito.`when`(userMapper.toResponse(user)).thenReturn(userResponse)

        webTestClient.get()
            .uri("/users/{id}", "1")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.name").isEqualTo(user.name)
            .jsonPath("$.email").isEqualTo(user.email)
    }

    @Test
    fun `should get all users`() {
        val users = Flux.just(user, user)
        val userResponseList = Flux.just(userResponse)

        Mockito.`when`(userService.findAll()).thenReturn(users)
        Mockito.`when`(userMapper.toResponse(user)).thenReturn(userResponse)

        webTestClient.get()
            .uri("/users")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(UserResponse::class.java)
            .hasSize(2)
            .contains(userResponse)
    }

    @Test
    fun `should update user successfully`() {
        val updatedUser = user.copy(name = "Gustavo Atualizado")
        val updatedRequest = userRequest.copy(name = "Gustavo Atualizado")

        Mockito.`when`(userService.update("1", updatedRequest)).thenReturn(Mono.just(updatedUser))
        Mockito.`when`(userMapper.toResponse(updatedUser)).thenReturn(userResponse.copy(name = "Gustavo Atualizado"))

        webTestClient.patch()
            .uri("/users/{id}", "1")
            .bodyValue(updatedRequest)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.name").isEqualTo("Gustavo Atualizado")
    }

    @Test
    fun `should delete user successfully`() {
        val userResponse = UserResponse(user.id, user.name, user.email, user.password)

        Mockito.`when`(userService.delete("1")).thenReturn(Mono.just(user))
        Mockito.`when`(userMapper.toResponse(user)).thenReturn(userResponse)

        webTestClient.delete()
            .uri("/users/{id}", "1")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.name").isEqualTo(userResponse.name)
            .jsonPath("$.email").isEqualTo(userResponse.email)
    }
}
