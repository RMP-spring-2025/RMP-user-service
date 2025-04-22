package org.healthapp.app.domain

import java.time.LocalDateTime

data class ProductConsumption(
    val user: User,
    val productId: Long,
    val mass: Double,
    val timeStamp: LocalDateTime
)