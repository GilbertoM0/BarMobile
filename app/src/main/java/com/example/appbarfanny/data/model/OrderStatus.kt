package com.example.appbarfanny.data.model

enum class OrderStatus(val displayText: String, val progress: Float) {
    RECIBIDO("Tu orden ha sido recibida", 0.25f),
    PREPARANDO("Tu orden está en preparación...", 0.60f),
    EN_CAMINO("¡El mesero va en camino!", 0.90f),
    ENTREGADO("Orden entregada. ¡Disfruta!", 1.0f)
}