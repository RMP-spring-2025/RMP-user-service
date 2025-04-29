package org.healthapp.app.domain

import java.time.LocalDateTime
import java.util.*

data class ProductConsumption(
    val user: UUID,
    val productId: Long,
    val mass: Double,
    val timeStamp: LocalDateTime
)