package com.omprakash.orderservspringkt

import com.omprakash.orderservspringkt.base.DatabaseContainerConfiguration
import com.omprakash.orderservspringkt.dto.request.AddProduct
import com.omprakash.orderservspringkt.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceIntegrationTest : DatabaseContainerConfiguration() {

    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var context: WebApplicationContext

    @Autowired
    lateinit var productService: ProductService

    @BeforeEach
    fun setup() {
//        cleanDB()
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build()
    }

    @Test
    fun `add product success`() {
        val addProductDto = AddProduct("P1", "Product 1", 10f)
        val savedProductResult = productService.addProduct(addProductDto)
        assertEquals(savedProductResult.isSuccess, true)
        val savedProduct = savedProductResult.getOrThrow()
        assertEquals(savedProduct.id, 1)
    }
}