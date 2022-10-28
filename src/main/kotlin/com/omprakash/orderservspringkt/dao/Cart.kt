package com.omprakash.orderservspringkt.dao

import com.omprakash.orderservspringkt.dto.request.AddCartItem
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.util.Date
import java.util.UUID
import javax.persistence.*

@Embeddable
class CartItemId(
    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "product_id", nullable = false)
    val product: Product,
) : Serializable

@Entity
@Table(name = "carts")
@IdClass(CartItemId::class)
data class Cart(
    @EmbeddedId
    val cartItemId: CartItemId,
    val orderRequestId: UUID? = null
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