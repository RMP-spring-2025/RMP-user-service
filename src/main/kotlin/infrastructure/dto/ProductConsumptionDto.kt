package org.healthapp.infrastructure.dto

import kotlinx.serialization.Serializable

@Serializable
class ProductConsumptionDto(
    val requestId: Long,
    val userId: Long,
    val productId: Long,
    val massConsumed: Double,
    val timestamp: String
)