package com.omprakash.orderservspringkt.service

import com.omprakash.orderservspringkt.dao.Product
import com.omprakash.orderservspringkt.dto.request.AddProduct
import com.omprakash.orderservspringkt.repository.ProductRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import javax.transaction.Transactional

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations= ["classpath:test.properties"])
internal class ProductServiceTest {
    private val productRepository = mockk<ProductRepository>()
    private val productService = ProductService(productRepository)

    @Test
    fun addProduct_Success() {
        val addProductDto = AddProduct("P1", "Product 1", 10f)
        val productDao = Product(addProductDto.name, addProductDto.description, addProductDto.price)
        every { productRepository.save(productDao) } returns productDao;
        val result = productService.addProduct(addProductDto)
        verify { productRepository.save(productDao) }
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, productDao)
    }

    @Test
    fun addProduct_Failure() {
        val addProductDto = AddProduct("P1", "Product 1", 10f)
        val productDao = Product(addProductDto.name, addProductDto.description, addProductDto.price)
        val productAlreadyExistsMessage = "Product with name already exists"
        every { productRepository.save(productDao) } throws Exception(productAlreadyExistsMessage);
        val result = productService.addProduct(addProductDto)
        verify { productRepository.save(productDao) }
        assertEquals(result.isFailure, true)
        assertThrows(AddProductInventoryException::class.java) {
            result.getOrThrow()
        }
    }

    @Test
    fun `get product details success`() {
        val addProductDto = AddProduct("P1", "Product 1", 10f)
        val productDao = Product(addProductDto.name, addProductDto.description, addProductDto.price)
        every { productRepository.findByName(addProductDto.name) } returns productDao;
        val result = productService.getProduct(addProductDto.name)
        verify { productRepository.findByName(addProductDto.name) }
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, productDao)
    }

    @Test
    fun `get product details failure`() {
        val addProductDto = AddProduct("P1", "Product 1", 10f)
        val productDao = Product(addProductDto.name, addProductDto.description, addProductDto.price)
        every { productRepository.findByName(addProductDto.name) } throws Exception("Product not found with name ${addProductDto.name}");
        val result = productService.getProduct(addProductDto.name)
        verify { productRepository.findByName(addProductDto.name) }
        assertEquals(result.isFailure, true)
        assertThrows(ProductNotFoundInInventoryException::class.java) {
            result.getOrThrow()
        }
    }
}