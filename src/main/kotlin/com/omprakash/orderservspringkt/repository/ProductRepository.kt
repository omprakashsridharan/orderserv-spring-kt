package com.omprakash.orderservspringkt.repository

import com.omprakash.orderservspringkt.dao.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
}