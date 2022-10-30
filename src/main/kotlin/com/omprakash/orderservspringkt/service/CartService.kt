package com.omprakash.orderservspringkt.service

import com.omprakash.orderservspringkt.dao.Cart
import com.omprakash.orderservspringkt.dao.CartItemId
import com.omprakash.orderservspringkt.dto.Events
import com.omprakash.orderservspringkt.dto.Request
import com.omprakash.orderservspringkt.producer.CreateOrderEventProducer
import com.omprakash.orderservspringkt.repository.CartRepository
import org.springframework.stereotype.Service
import java.util.*

class AddProductToCartException(message: String) : Exception(message)

class CheckoutCartException(message: String) : Exception("CheckoutCartError: $message")
class EmptyCartException(message: String) : Exception("EmptyCartError: $message")

@Service
class CartService(
    val userService: UserService,
    val productService: ProductService,
    val cartRepository: CartRepository,
    val createOrderEventProducer: CreateOrderEventProducer
) {
    fun addProductToCart(addCartItem: Request.AddCartItem): Result<Cart> {
        return try {
            val user = userService.getUserByEmail(addCartItem.email).getOrThrow()
            val product = productService.getProductByName(addCartItem.productName).getOrThrow()
            val cartItemId = CartItemId(user, product)
            val cartItem = Cart(cartItemId)
            val result = cartRepository.saveAndFlush(cartItem)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(AddProductToCartException(e.message ?: "Error while creating user"))
        }
    }

    fun checkout(checkoutCart: Request.CheckoutCart): Result<UUID> {
        return try {
            val user = userService.getUserByEmail(checkoutCart.email).getOrThrow()
            val userId = user.id ?: throw UserNotFoundException("User ID null")
            val cartItems = cartRepository.findAllByCartItemId_User(userId)
            if (cartItems.isEmpty()) {
                throw EmptyCartException("No products to checkout")
            }
            createOrderEventProducer.sendCreateOrderEvent(Events.CreateOrder(user.email))
                .onSuccess {
                    cartRepository.deleteAllByIdInBatch(cartItems.map { it.cartItemId })
                }.onFailure {
                    throw it
                }
            Result.success(UUID.randomUUID())
        } catch (e: Exception) {
            Result.failure(CheckoutCartException(e.message ?: "Error while creating user"))
        }
    }
}