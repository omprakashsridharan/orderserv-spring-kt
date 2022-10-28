package com.omprakash.orderservspringkt.dao

import com.omprakash.orderservspringkt.dto.request.CreateUser
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.Date
import javax.persistence.*

enum class UserRole {
    ADMIN, USER
}

@Entity
@Table(name = "users")
data class User(
    @Column(unique = true)
    var email: String,
    var password: String,
    var address: String,
    @Enumerated(value = EnumType.STRING)
    val role: UserRole = UserRole.USER,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user")
    var cartItems: Set<Cart>? = null
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
        fun fromDto(createUser: CreateUser) =
            User(email = createUser.email, password = createUser.password, address = createUser.address)
    }

}