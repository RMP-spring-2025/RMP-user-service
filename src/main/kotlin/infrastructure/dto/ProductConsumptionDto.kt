package org.healthapp.infrastructure.dto

import kotlinx.serialization.Serializable
import org.healthapp.util.UUIDSerializer
import java.util.*

@Serializable
class ProductConsumptionDto(
    @Serializable(with = UUIDSerializer::class)
    val requestId: UUID,
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    val productId: Long,
    val massConsumed: Double,
    val timestamp: String
)