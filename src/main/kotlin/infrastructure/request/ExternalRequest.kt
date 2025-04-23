package org.healthapp.infrastructure.request

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import org.healthapp.util.UUIDSerializer
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("type")
sealed class ExternalRequest {
    abstract val requestId: UUID

    @Serializable
    @SerialName("get_products_by_ids")
    data class GetProductById(
        @Serializable(with = UUIDSerializer::class)
        override val requestId: UUID,
        @SerialName("ids")
        val productId: List<Long>
    ) : ExternalRequest()
}