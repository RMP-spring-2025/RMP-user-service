package org.healthapp.infrastructure.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ExternalResponse(
    @SerialName("request_id")
    val requestId: String,
    val products: List<Product>
)

@Serializable
data class Product(
    @SerialName("product_id")
    val productId: Long,
    val name: String,
    val calories: Int,
    @SerialName("B")
    val proteins: Int? = null,
    @SerialName("Z")
    val fats: Int? = null,
    @SerialName("U")
    val carbohydrates: Int? = null,
    val mass: Int
)