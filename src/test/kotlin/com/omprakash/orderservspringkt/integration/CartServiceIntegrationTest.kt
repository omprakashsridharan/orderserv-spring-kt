package com.omprakash.orderservspringkt.integration

import com.omprakash.orderservspringkt.base.DatabaseContainerConfiguration
import com.omprakash.orderservspringkt.dto.request.AddCartItem
import com.omprakash.orderservspringkt.dto.request.CreateProduct
import com.omprakash.orderservspringkt.dto.request.CreateUser
import com.omprakash.orderservspringkt.service.CartService
import com.omprakash.orderservspringkt.service.ProductService
import com.omprakash.orderservspringkt.service.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers
import javax.transaction.Transactional

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest
class CartServiceIntegrationTest : DatabaseContainerConfiguration() {

    @Autowired
    lateinit var cartService: CartService

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var productService: ProductService

    @Test
    @Transactional
    fun `add product to cart success`() {
        val createProductDto = CreateProduct("P1", "Product 1", 10f)
        val savedProductResult = productService.createProduct(createProductDto)
        Assertions.assertEquals(true, savedProductResult.isSuccess)

        val createUserDto = CreateUser("test@test.com", "password", "address")
        val savedUserResult = userService.createUser(createUserDto)
        Assertions.assertEquals(true, savedUserResult.isSuccess)

        val addCartItemDto = AddCartItem(createUserDto.email, createProductDto.name)
        val addProductToCartResult = cartService.addProductToCart(addCartItemDto)
        Assertions.assertEquals(true, addProductToCartResult.isSuccess)
    }
}