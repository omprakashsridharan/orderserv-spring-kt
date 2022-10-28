package com.omprakash.orderservspringkt.service

import com.omprakash.orderservspringkt.dao.User
import com.omprakash.orderservspringkt.dto.request.CreateUser
import com.omprakash.orderservspringkt.repository.UserRepository
import org.springframework.stereotype.Service

class CreateUserException(message: String) : Exception(message)
class UserNotFoundException(message: String) : Exception(message)

@Service
class UserService(val userRepository: UserRepository) {
    fun createUser(createUser: CreateUser): Result<User> {
        return try {
            val userDao = User.fromDto(createUser)
            val result = userRepository.save(userDao)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(CreateUserException(e.message ?: "Error while creating user"))
        }
    }

    fun getUserByEmail(email: String): Result<User> {
        return try {
            userRepository.findByEmail(email)?.let {
                Result.success(it)
            } ?: Result.failure(UserNotFoundException("user with email $email not found in DB"))

        } catch (e: Exception) {
            Result.failure(CreateUserException(e.message ?: "Error while creating user"))
        }
    }
}