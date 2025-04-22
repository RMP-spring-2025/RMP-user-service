package org.healthapp.infrastructure.request


import LocalDateTimeSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import org.healthapp.util.UUIDSerializer
import java.time.LocalDateTime
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("requestType")
sealed class Request {
    abstract val requestId: UUID
    abstract val userId: UUID
    abstract val requestType: String

    @Serializable
    @SerialName("add_product")
    data class AddProductRequest(
        @Serializable(with = UUIDSerializer::class)
        @SerialName("requestId") override val requestId: UUID,
        @Serializable(with = UUIDSerializer::class)
        @SerialName("userId") override val userId: UUID,
        override val requestType: String,
        @SerialName("productId") val productId: Long,
        @Serializable(with = LocalDateTimeSerializer::class)
        val time: LocalDateTime,
        @SerialName("massConsumed") val massConsumed: Double
    ) : Request()

    @Serializable
    @SerialName("get_calories")
    data class GetCaloriesRequest(
        @Serializable(with = UUIDSerializer::class)
        @SerialName("requestId") override val requestId: UUID,
        @Serializable(with = UUIDSerializer::class)
        @SerialName("userId") override val userId: UUID,
        override val requestType: String,
        @Serializable(with = LocalDateTimeSerializer::class)
        val from: LocalDateTime,
        @Serializable(with = LocalDateTimeSerializer::class)
        val to: LocalDateTime
    ) : Request()
}