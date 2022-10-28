package com.omprakash.orderservspringkt

import com.omprakash.orderservspringkt.base.DatabaseContainerConfiguration
import com.omprakash.orderservspringkt.dto.request.AddProduct
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
        val addProductDto = AddProduct("P1", "Product 1", 10f)
        val savedProductResult = productService.addProduct(addProductDto)
        assertEquals(true, savedProductResult.isSuccess)
    }

    @Test
    @Transactional
    fun `add product throws error on product with name of existing product in DB`() {
        val addProductDto = AddProduct("P1", "Product 1", 10f)
        val savedProductResult1 = productService.addProduct(addProductDto)
        assertEquals(true, savedProductResult1.isSuccess)
        val savedProduct = savedProductResult1.getOrThrow()
        val savedProductResult2 = productService.addProduct(addProductDto)
        assertEquals(true,savedProductResult2.isFailure)
        assertThrows(AddProductInventoryException::class.java) {
            savedProductResult2.getOrThrow()
        }
    }

    @Test
    @Transactional
    fun `get details of existing product in DB by name`() {
        val addProductDto = AddProduct("P1", "Product 1", 10f)
        val savedProductResult = productService.addProduct(addProductDto)
        assertEquals(true, savedProductResult.isSuccess)
        val getProductDetailsResult = productService.getProduct(addProductDto.name)
        assertEquals(true, getProductDetailsResult.isSuccess)
    }

    @Test
    @Transactional
    fun `get details of unavailable product in DB by name`() {
        val getProductDetailsResult = productService.getProduct("UNAVAILABLE")
        assertEquals(true, getProductDetailsResult.isFailure)
        assertThrows(ProductNotFoundInInventoryException::class.java) {
            getProductDetailsResult.getOrThrow()
        }
    }
}