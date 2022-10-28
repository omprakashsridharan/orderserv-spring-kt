package com.omprakash.orderservspringkt.service

import com.omprakash.orderservspringkt.dao.Product
import com.omprakash.orderservspringkt.dto.request.AddProduct
import com.omprakash.orderservspringkt.repository.ProductRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import javax.transaction.Transactional

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
}