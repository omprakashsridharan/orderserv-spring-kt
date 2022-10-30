package com.omprakash.orderservspringkt.dto

object Request {
    data class CreateUser(
        var email: String,
        var password: String,
        var address: String,
    )

    data class CreateProduct(
        var name: String,
        var description: String,
        var price: Float,
    )

    data class AddCartItem(val email: String, val productName: String)

    data class CheckoutCart(val email: String)
}