package com.omprakash.orderservspringkt.repository

import com.omprakash.orderservspringkt.dao.Product
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : CrudRepository<Product, Long> {
}