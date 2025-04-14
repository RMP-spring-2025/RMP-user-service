package org.healthapp.infrastructure.request

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import org.healthapp.util.UUIDSerializer
import java.util.UUID

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("requestType")
sealed class Request {
    abstract val requestId: UUID
    abstract val userId: Long
    abstract val requestType: String

    @Serializable
    @SerialName("add_product")
    data class AddProductRequest(
        @Serializable(with = UUIDSerializer::class)
        @SerialName("request_id") override val requestId: UUID,
        @SerialName("user_id") override val userId: Long,
        override val requestType: String,
        @SerialName("product_id") val productId: Long,
        val date: String,
        @SerialName("mass_consumed") val massConsumed: Double
    ) : Request()

    @Serializable
    @SerialName("get_calories")
    data class GetCaloriesRequest(
        @Serializable(with = UUIDSerializer::class)
        @SerialName("request_id") override val requestId: UUID,
        @SerialName("user_id") override val userId: Long,
        override val requestType: String,
        val from: String,
        val to: String
    ) : Request()
}