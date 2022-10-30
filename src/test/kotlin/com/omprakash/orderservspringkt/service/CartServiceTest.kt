package com.omprakash.orderservspringkt.service

import com.omprakash.orderservspringkt.dao.Cart
import com.omprakash.orderservspringkt.dao.CartItemId
import com.omprakash.orderservspringkt.dao.Product
import com.omprakash.orderservspringkt.dao.User
import com.omprakash.orderservspringkt.dto.Request
import com.omprakash.orderservspringkt.repository.CartRepository
import io.mockk.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.util.UUID

internal class CartServiceTest {
    private val cartRepository = mockk<CartRepository>()
    private val userService = mockk<UserService>()
    private val productService = mockk<ProductService>()
    private val cartService = CartService(userService, productService, cartRepository)

    private val email = "test@test.com"
    private val productDao1 = Product("P1", "Product 1", 10f)
    private val productDao2 = Product("P2", "Product 2", 20f)
    private val userDao = User(email, "PASSWORD", "ADDRESS")
    private val checkoutCartDto = Request.CheckoutCart(email)
    private val uuid = UUID.fromString("081cd2c0-0d03-4de2-ad5a-1fd3f01cf9f6")

    @BeforeEach
    fun beforeEach() {
        mockkStatic(UUID::class)
        userDao.id = 1L
    }

    @Test
    fun `checkout success`() {
        val cartDaoItems = listOf(Cart(CartItemId(userDao, productDao1)), Cart(CartItemId(userDao, productDao2)))
        every { userService.getUserByEmail(checkoutCartDto.email) } returns Result.success(userDao)
        every { cartRepository.findAllByIdUser(userDao.id!!) } returns cartDaoItems
        every { UUID.randomUUID() } returns uuid
        val result = cartService.checkout(checkoutCartDto)
        verify {
            userService.getUserByEmail(checkoutCartDto.email)
            cartRepository.findAllByIdUser(userDao.id!!)
        }
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, uuid)
    }
}