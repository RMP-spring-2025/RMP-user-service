package org.healthapp.infrastructure.request

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import org.healthapp.util.UUIDSerializer
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("requestType")
sealed class ExternalRequest {
    abstract val requestId: UUID

    @Serializable
    @SerialName("get_product_by_id")
    data class GetProductById(
        @Serializable(with = UUIDSerializer::class)
        override val requestId: UUID,
        val productId: List<Long>
    ) : ExternalRequest()
}