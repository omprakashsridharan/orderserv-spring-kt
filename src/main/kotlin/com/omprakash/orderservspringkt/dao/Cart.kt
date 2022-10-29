package com.omprakash.orderservspringkt.dao

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Embeddable
data class CartItemId(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User, @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    var product: Product
) : Serializable

@Entity
@Table(name = "carts", uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "product_id"])])
data class Cart(
    @EmbeddedId
    val cartItemId: CartItemId,
) {

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    lateinit var createdAt: Date

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    lateinit var updatedAt: Date

    companion object {
    }

}