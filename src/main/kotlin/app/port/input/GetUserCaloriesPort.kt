package org.healthapp.app.port.input

import org.healthapp.infrastructure.dto.ProductStatDTO
import org.healthapp.infrastructure.response.ExternalResponse
import org.healthapp.infrastructure.response.StatEntryDTO
import java.time.LocalDateTime
import java.util.*

interface GetUserCaloriesPort {
    fun getUserCalories(userId: UUID, from: LocalDateTime, to: LocalDateTime): List<ProductStatDTO>

    fun calculateCalories(productStats: List<ProductStatDTO>, response: ExternalResponse): List<StatEntryDTO>
}