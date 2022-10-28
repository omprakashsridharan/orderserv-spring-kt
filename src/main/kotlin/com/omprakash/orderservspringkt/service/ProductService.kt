package com.omprakash.orderservspringkt.service

import com.omprakash.orderservspringkt.dao.Product
import com.omprakash.orderservspringkt.dto.request.AddProduct
import com.omprakash.orderservspringkt.repository.ProductRepository
import org.springframework.stereotype.Service

class AddProductInventoryException(message: String) : Exception(message)

@Service
class ProductService(var productRepository: ProductRepository) {
    fun addProduct(addProduct: AddProduct): Result<Product> {
        return try {
            val productDao = Product.fromDto(addProduct)
            val result = productRepository.save(productDao)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(AddProductInventoryException(e.message ?: "Error while adding product to inventory"))
        }
    }
}