package org.healthapp.infrastructure.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ExternalResponse(
    @SerialName("requestId")
    val requestId: String,
    val products: List<Product>
)

@Serializable
data class Product(
    @SerialName("productId")
    val productId: Long,
    val name: String,
    val calories: Double,
    @SerialName("B")
    val B: Double? = null,
    @SerialName("Z")
    val Z: Double? = null,
    @SerialName("U")
    val U: Double? = null,
    val mass: Double
)