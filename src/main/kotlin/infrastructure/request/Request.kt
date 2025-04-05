package org.healthapp.infrastructure.request

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("type")
sealed class Request {
    abstract val requestId: Long
    abstract val userId: Long
    abstract val type: String

    @Serializable
    @SerialName("add_product")
    data class AddProductRequest(
        @SerialName("request_id") override val requestId: Long,
        @SerialName("user_id") override val userId: Long,
        override val type: String,
        @SerialName("product_id") val productId: Long,
        val date: String,
        @SerialName("mass_consumed") val massConsumed: Double
    ) : Request()
}