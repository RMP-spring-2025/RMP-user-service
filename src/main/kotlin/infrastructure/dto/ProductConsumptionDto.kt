package org.healthapp.infrastructure.dto

import kotlinx.serialization.Serializable
import org.healthapp.util.UUIDSerializer
import java.util.UUID

@Serializable
class ProductConsumptionDto(
    @Serializable(with = UUIDSerializer::class)
    val requestId: UUID,
    val userId: Long,
    val productId: Long,
    val massConsumed: Double,
    val timestamp: String
)