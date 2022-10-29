package com.omprakash.orderservspringkt.repository

import com.omprakash.orderservspringkt.dao.Cart
import com.omprakash.orderservspringkt.dao.CartItemId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartRepository : JpaRepository<Cart, CartItemId> {
}