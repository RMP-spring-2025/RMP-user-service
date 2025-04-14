package org.healthapp.app.port.input

import org.healthapp.infrastructure.dto.Response
import org.healthapp.infrastructure.dto.StatEntryDTO

interface GetUserCaloriesPort {
    fun getUserCalories(userId: Long, from: String, to: String): List<StatEntryDTO>
}