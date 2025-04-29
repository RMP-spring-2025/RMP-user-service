package org.healthapp.infrastructure.dto

import LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import org.healthapp.util.UUIDSerializer
import java.time.LocalDateTime
import java.util.*

@Serializable
class ProductConsumptionDTO(
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    val productId: Long,
    val massConsumed: Double,
    @Serializable(with = LocalDateTimeSerializer::class)
    val timestamp: LocalDateTime
)