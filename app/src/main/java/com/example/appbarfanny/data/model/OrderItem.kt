package com.example.appbarfanny.data.model

data class OrderItem(
    val bebida: Bebida,
    var quantity: Int = 1
) {
    val subtotal: Double
        get() = bebida.precio * quantity
}