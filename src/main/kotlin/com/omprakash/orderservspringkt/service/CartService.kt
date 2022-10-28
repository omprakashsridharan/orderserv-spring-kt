package com.omprakash.orderservspringkt.service

import com.omprakash.orderservspringkt.dao.Cart
import com.omprakash.orderservspringkt.dao.CartItemId
import com.omprakash.orderservspringkt.dto.request.AddCartItem
import com.omprakash.orderservspringkt.repository.CartRepository
import org.springframework.stereotype.Service

class AddProductToCartException(message: String) : Exception(message)

@Service
class CartService(
    val userService: UserService,
    val productService: ProductService,
    val cartRepository: CartRepository
) {
    fun addProductToCart(addCartItem: AddCartItem): Result<Cart> {
        return try {
            val user = userService.getUserByEmail(addCartItem.email).getOrThrow()
            val product = productService.getProductByName(addCartItem.productName).getOrThrow()
            val cartItem = Cart(CartItemId(user,product))
            val result = cartRepository.save(cartItem)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(AddProductToCartException(e.message ?: "Error while creating user"))
        }
    }
}