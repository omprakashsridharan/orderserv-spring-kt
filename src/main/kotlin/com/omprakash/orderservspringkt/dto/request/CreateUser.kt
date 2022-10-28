package com.omprakash.orderservspringkt.dto.request

data class CreateUser(
    var email: String,
    var password: String,
    var address: String,
)