package com.omprakash.orderservspringkt.service

import com.omprakash.orderservspringkt.dao.Product
import com.omprakash.orderservspringkt.dto.request.CreateProduct
import com.omprakash.orderservspringkt.repository.ProductRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations= ["classpath:test.properties"])
internal class ProductServiceTest {
    private val productRepository = mockk<ProductRepository>()
    private val productService = ProductService(productRepository)

    @Test
    fun `create product success`() {
        val createProductDto = CreateProduct("P1", "Product 1", 10f)
        val productDao = Product(createProductDto.name, createProductDto.description, createProductDto.price)
        every { productRepository.save(productDao) } returns productDao;
        val result = productService.createProduct(createProductDto)
        verify { productRepository.save(productDao) }
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, productDao)
    }

    @Test
    fun `create product failure`() {
        val createProductDto = CreateProduct("P1", "Product 1", 10f)
        val productDao = Product(createProductDto.name, createProductDto.description, createProductDto.price)
        val productAlreadyExistsMessage = "Product with name already exists"
        every { productRepository.save(productDao) } throws Exception(productAlreadyExistsMessage);
        val result = productService.createProduct(createProductDto)
        verify { productRepository.save(productDao) }
        assertEquals(result.isFailure, true)
        assertThrows(AddProductInventoryException::class.java) {
            result.getOrThrow()
        }
    }

    @Test
    fun `get product details success`() {
        val createProductDto = CreateProduct("P1", "Product 1", 10f)
        val productDao = Product(createProductDto.name, createProductDto.description, createProductDto.price)
        every { productRepository.findByName(createProductDto.name) } returns productDao;
        val result = productService.getProductByName(createProductDto.name)
        verify { productRepository.findByName(createProductDto.name) }
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, productDao)
    }

    @Test
    fun `get product details failure`() {
        val createProductDto = CreateProduct("P1", "Product 1", 10f)
        val productDao = Product(createProductDto.name, createProductDto.description, createProductDto.price)
        every { productRepository.findByName(createProductDto.name) } throws Exception("Product not found with name ${createProductDto.name}");
        val result = productService.getProductByName(createProductDto.name)
        verify { productRepository.findByName(createProductDto.name) }
        assertEquals(result.isFailure, true)
        assertThrows(ProductNotFoundInInventoryException::class.java) {
            result.getOrThrow()
        }
    }
}