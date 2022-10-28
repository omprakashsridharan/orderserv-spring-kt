package com.omprakash.orderservspringkt.dao

import com.omprakash.orderservspringkt.dto.request.AddProduct
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "products")
data class Product (
    @Column(unique = true)
    var name: String,
    var description: String,
    var price: Float,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    lateinit var createdAt: Date

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    lateinit var updatedAt: Date

    companion object {
        fun fromDto(dto: AddProduct) = Product(
            name = dto.name,
            description = dto.description,
            price = dto.price
        )
    }

}