package org.healthapp.infrastructure.dto

import kotlinx.serialization.Serializable

@Serializable
data class RequestProductServiceDTO(
    val data: String,
    val productId: Long,
    val massConsumed: Double
)