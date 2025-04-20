package org.healthapp.app.port.input

import org.healthapp.infrastructure.dto.StatEntryDTO
import java.util.UUID

interface GetUserCaloriesPort {
    fun getUserCalories(userId: UUID, from: String, to: String): List<StatEntryDTO>
}