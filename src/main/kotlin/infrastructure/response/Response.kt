package org.healthapp.infrastructure.response

import kotlinx.serialization.Serializable
import org.healthapp.app.domain.UserGoal
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

    @Serializable
    data class UserStatResponse(
        @Serializable(with = UUIDSerializer::class)
        override val requestId: UUID,
        @Serializable(with = UUIDSerializer::class)
        val userId: UUID,
        val username: String,
        val weight: Double?,
        val height: Double,
        val age: Int,
        val goal: UserGoal

    ): Response()


}


