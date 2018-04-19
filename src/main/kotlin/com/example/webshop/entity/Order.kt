package com.example.webshop.entity

import javax.persistence.*

@Entity
@Table(name = "orders")
data class Order(
        val status: String,
        @Id @GeneratedValue
        val id: Long = -1,
        @ManyToOne
        val shop: Shop
)
