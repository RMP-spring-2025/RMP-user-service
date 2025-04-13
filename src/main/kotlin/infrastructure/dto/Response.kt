package org.healthapp.infrastructure.dto

import kotlinx.serialization.Serializable

@Serializable
sealed class Response {
    abstract val requestId: Long

    @Serializable
    data class CaloriesResponse(
        override val requestId: Long,
        val stats: List<StatEntryDTO>
    ) : Response()

    @Serializable
    data class SuccessResponse(
        override val requestId: Long,
        val message: String
    ) : Response()
}


