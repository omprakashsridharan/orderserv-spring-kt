package com.omprakash.orderservspringkt.dto.request

data class AddProduct(
    var name: String,
    var description: String,
    var price: Float,
)