package com.omprakash.orderservspringkt.integration

import com.omprakash.orderservspringkt.base.DatabaseContainerConfiguration
import com.omprakash.orderservspringkt.dto.request.CreateProduct
import com.omprakash.orderservspringkt.service.AddProductInventoryException
import com.omprakash.orderservspringkt.service.ProductNotFoundInInventoryException
import com.omprakash.orderservspringkt.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers
import javax.transaction.Transactional

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest
class ProductServiceIntegrationTest : DatabaseContainerConfiguration() {

    @Autowired
    lateinit var productService: ProductService

    @Test
    @Transactional
    fun `add product success`() {
        val createProductDto = CreateProduct("P1", "Product 1", 10f)
        val savedProductResult = productService.createProduct(createProductDto)
        assertEquals(true, savedProductResult.isSuccess)
    }

    @Test
    @Transactional
    fun `add product throws error on product with name of existing product in DB`() {
        val createProductDto = CreateProduct("P1", "Product 1", 10f)
        val savedProductResult1 = productService.createProduct(createProductDto)
        assertEquals(true, savedProductResult1.isSuccess)
        val savedProduct = savedProductResult1.getOrThrow()
        val savedProductResult2 = productService.createProduct(createProductDto)
        assertEquals(true,savedProductResult2.isFailure)
        assertThrows(AddProductInventoryException::class.java) {
            savedProductResult2.getOrThrow()
        }
    }

    @Test
    @Transactional
    fun `get details of existing product in DB by name`() {
        val createProductDto = CreateProduct("P1", "Product 1", 10f)
        val savedProductResult = productService.createProduct(createProductDto)
        assertEquals(true, savedProductResult.isSuccess)
        val getProductDetailsResult = productService.getProductByName(createProductDto.name)
        assertEquals(true, getProductDetailsResult.isSuccess)
    }

    @Test
    @Transactional
    fun `get details of unavailable product in DB by name`() {
        val getProductDetailsResult = productService.getProductByName("UNAVAILABLE")
        assertEquals(true, getProductDetailsResult.isFailure)
        assertThrows(ProductNotFoundInInventoryException::class.java) {
            getProductDetailsResult.getOrThrow()
        }
    }
}