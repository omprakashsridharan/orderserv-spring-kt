package com.omprakash.orderservspringkt.integration

import com.omprakash.orderservspringkt.base.DatabaseContainerConfiguration
import com.omprakash.orderservspringkt.dto.Request.AddCartItem
import com.omprakash.orderservspringkt.dto.Request.CreateProduct
import com.omprakash.orderservspringkt.dto.Request.CreateUser
import com.omprakash.orderservspringkt.repository.CartRepository
import com.omprakash.orderservspringkt.service.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.testcontainers.junit.jupiter.Testcontainers
import javax.transaction.Transactional

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
class CartServiceIntegrationTest : DatabaseContainerConfiguration() {

    @Autowired
    lateinit var cartService: CartService

    @Autowired
    lateinit var cartRepository: CartRepository

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var productService: ProductService

    @Test
    @Transactional
    fun `add product to cart success`() {
        val createProductDto = CreateProduct("P1", "Product 1", 10f)
        val savedProductResult = productService.createProduct(createProductDto)
        assertEquals(true, savedProductResult.isSuccess)

        val createUserDto = CreateUser("test@test.com", "password", "address")
        val savedUserResult = userService.createUser(createUserDto)
        assertEquals(true, savedUserResult.isSuccess)

        val addCartItemDto = AddCartItem(createUserDto.email, createProductDto.name)
        val addProductToCartResult = cartService.addProductToCart(addCartItemDto)
        assertEquals(true, addProductToCartResult.isSuccess)
    }

    @Test
    @Transactional
    fun `add existing product for existing user to cart second time does not create a duplicate cart item`() {
        val createProductDto = CreateProduct("P1", "Product 1", 10f)
        val savedProductResult = productService.createProduct(createProductDto)
        assertEquals(true, savedProductResult.isSuccess)

        val createUserDto = CreateUser("test@test.com", "password", "address")
        val savedUserResult = userService.createUser(createUserDto)
        assertEquals(true, savedUserResult.isSuccess)

        val addCartItemDto1 = AddCartItem(createUserDto.email, createProductDto.name)
        val addProductToCartResult1 = cartService.addProductToCart(addCartItemDto1)
        assertEquals(true, addProductToCartResult1.isSuccess)

        val addCartItemDto2 = AddCartItem(createUserDto.email, createProductDto.name)
        val addProductToCartResult2 = cartService.addProductToCart(addCartItemDto2)
        assertEquals(true, addProductToCartResult2.isSuccess)

        val numberOfCartItems = cartRepository.findAll()
        assertEquals(1, numberOfCartItems.size)
    }
}