package com.omprakash.orderservspringkt.dto.request

data class CreateProduct(
    var name: String,
    var description: String,
    var price: Float,
)