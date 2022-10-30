package com.omprakash.orderservspringkt.service

import com.omprakash.orderservspringkt.dao.User
import com.omprakash.orderservspringkt.dto.Request
import com.omprakash.orderservspringkt.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class UserServiceTest {
    private val userRepository = mockk<UserRepository>()
    private val userService = UserService(userRepository)

    @Test
    fun `create user success`() {
        val createUserDto = Request.CreateUser("test@test.com", "password", "ADDRESS")
        val userDao = User.fromDto(createUserDto)
        every { userRepository.save(userDao) } returns userDao;
        val result = userService.createUser(createUserDto)
        verify { userRepository.save(userDao) }
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, userDao)
    }

    @Test
    fun `create user failure`() {
        val createUserDto = Request.CreateUser("test@test.com", "password", "ADDRESS")
        val userDao = User.fromDto(createUserDto)
        every { userRepository.save(userDao) } throws Exception("Error while saving user");
        val result = userService.createUser(createUserDto)
        verify { userRepository.save(userDao) }
        assertEquals(result.isFailure, true)
        assertThrows(CreateUserException::class.java) {
            result.getOrThrow()
        }
    }
}