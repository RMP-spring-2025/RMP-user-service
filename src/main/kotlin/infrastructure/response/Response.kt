package org.healthapp.infrastructure.response

import kotlinx.serialization.Serializable
import org.healthapp.util.UUIDSerializer
import java.util.*

@Serializable
sealed class Response {
    abstract val requestId: UUID

    @Serializable
    data class StatisticResponse(
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

    @Serializable
    data class FailureResponse(
        @Serializable(with = UUIDSerializer::class)
        override val requestId: UUID,
        val message: String
    ) : Response()


}


