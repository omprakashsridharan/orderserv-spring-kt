package com.omprakash.orderservspringkt.repository

import com.omprakash.orderservspringkt.dao.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}