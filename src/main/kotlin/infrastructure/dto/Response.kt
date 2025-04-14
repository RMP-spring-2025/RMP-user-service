package org.healthapp.infrastructure.dto

import kotlinx.serialization.Serializable
import org.healthapp.util.UUIDSerializer
import java.util.UUID

@Serializable
sealed class Response {
    abstract val requestId: UUID

    @Serializable
    data class CaloriesResponse(
        @Serializable(with = UUIDSerializer::class)
        override val requestId: UUID,
        val stats: List<StatEntryDTO>
    ) : Response()

    @Serializable
    data class SuccessResponse(
        @Serializable(with = UUIDSerializer::class)
        override val requestId: UUID,
        val message: String
    ) : Response()
}


