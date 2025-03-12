package com.udemy.webflux.service

import com.udemy.webflux.Builders
import com.udemy.webflux.entity.User
import com.udemy.webflux.mapper.UserMapper
import com.udemy.webflux.repository.UserRepository
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import kotlin.test.assertEquals
import kotlin.test.assertNotNull



@ExtendWith(MockKExtension::class)
class UserServiceTest {

    private val builder = Builders()

    private val userRepository: UserRepository = mockk()

    private val userMapper: UserMapper = mockk()

    private val userService: UserService = UserService(userRepository, userMapper)

    private val id: String = "123"

    @Test
    fun `should save a user`() {
        val userRequest = builder.userRequestBuilder(name = "Gustavo", email = "Teste@Mockk")
        val userEntity = builder.userBuilder(name = "Gustavo", email = "Teste@Mockk")

        every{userMapper.toEntity(userRequest)} returns userEntity
        every {userRepository.save(userEntity)} returns Mono.just(userEntity)

        val result = userService.save(userRequest)

        StepVerifier.create(result)
            .assertNext{ savedUser ->
                assertNotNull(savedUser)
                assertEquals("Gustavo", savedUser.name)
                assertEquals("Teste@Mockk", savedUser.email)
            }
            .verifyComplete()

        verify(exactly = 1) {userMapper.toEntity(userRequest)}
        verify(exactly = 1) {userRepository.save(userEntity)}
    }

    @Test
    fun `should save a name without spaces at the end`(){
        val request = builder.userRequestBuilder()
        val entity = builder.userBuilder(name = "Texto     ", email = "Teste@Mockk          ")

        every{userMapper.toEntity(request)} returns entity
        every {userRepository.save(entity)} returns Mono.just(entity)

        val result = userService.save(request)

        StepVerifier.create(result)
            .assertNext{ savedUser ->
                assertNotNull(savedUser)
                assertEquals("Texto", savedUser.name)
                assertEquals("Teste@Mockk", savedUser.email)
            }
            .verifyComplete()

        verify(exactly = 1) {userMapper.toEntity(request)}
        verify(exactly = 1) {userRepository.save(entity)}
    }

    @Test
    fun  `should return a user by id`(){
        val user: User = builder.userBuilder(id = id)

        every {userRepository.findById(id)} returns Mono.just(user)
        val result = userService.findById(id)

        StepVerifier.create(result)
            .assertNext{ foundUser ->
                assertNotNull(foundUser)
                assertEquals(id, foundUser.id)
            }
            .verifyComplete()

        verify(exactly = 1) {userRepository.findById(id)}
    }

    @Test
    fun `should find all users`() {
        val users = listOf(builder.userBuilder(), builder.userBuilder())
        val fluxUsers = Flux.fromIterable(users)

        every { userRepository.findAll() } returns fluxUsers

        val result = userService.findAll()

        StepVerifier.create(result)
            .expectNextMatches { user -> user == users[0] }
            .expectNextMatches { user -> user == users[1] }
            .verifyComplete()

        verify(exactly = 1) { userRepository.findAll() }
    }

    @Test
    fun `should update a user`() {
        val userRequest = builder.userRequestBuilder(name = "Updated Name", email = "updated@mockk.com")
        val existingUser = builder.userBuilder(id = id, name = "Original Name", email = "original@mockk.com")
        val updatedUser = builder.userBuilder(id = id, name = "Updated Name", email = "updated@mockk.com")

        every { userRepository.findById(id) } returns Mono.just(existingUser)
        every { userMapper.toEntity(userRequest, existingUser) } returns updatedUser
        every { userRepository.save(updatedUser) } returns Mono.just(updatedUser)

        val result = userService.update(id, userRequest)

        StepVerifier.create(result)
            .assertNext { user ->
                assertNotNull(user)
                assertEquals("Updated Name", user.name)
                assertEquals("updated@mockk.com", user.email)
            }
            .verifyComplete()

        verify(exactly = 1) { userRepository.findById(id) }
        verify(exactly = 1) { userMapper.toEntity(userRequest, existingUser) }
        verify(exactly = 1) { userRepository.save(updatedUser) }
    }

    @Test
    fun `should delete a user`() {

        val user = builder.userBuilder(id = id)

        every { userRepository.findById(id) } returns Mono.just(user)
        every { userRepository.delete(user) } returns Mono.empty()

        val result = userService.delete(id)

        StepVerifier.create(result)
            .assertNext { deletedUser ->
                assertNotNull(deletedUser)
                assertEquals(id, deletedUser.id)
            }
            .verifyComplete()

        verify { userRepository.findById(id)  }
        verify { userRepository.delete(user)}


    }



}