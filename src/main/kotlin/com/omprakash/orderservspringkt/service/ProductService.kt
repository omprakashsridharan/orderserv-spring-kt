package com.omprakash.orderservspringkt.service

import com.omprakash.orderservspringkt.dao.Product
import com.omprakash.orderservspringkt.dto.Request
import com.omprakash.orderservspringkt.repository.ProductRepository
import org.springframework.stereotype.Service

class AddProductInventoryException(message: String) : Exception(message)
class ProductNotFoundInInventoryException(message: String) : Exception(message)

@Service
class ProductService(var productRepository: ProductRepository) {
    fun createProduct(createProduct: Request.CreateProduct): Result<Product> {
        return try {
            val productDao = Product.fromDto(createProduct)
            val result = productRepository.save(productDao)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(AddProductInventoryException(e.message ?: "Error while adding product to inventory"))
        }
    }

    fun getProductByName(productName: String): Result<Product> {
        return try {
            productRepository.findByName(productName)?.let {
                Result.success(it)
            } ?: Result.failure(ProductNotFoundInInventoryException("Product by name $productName not present"))

        } catch (e: Exception) {
            Result.failure(ProductNotFoundInInventoryException(e.message ?: "Error while adding product to inventory"))
        }
    }
}