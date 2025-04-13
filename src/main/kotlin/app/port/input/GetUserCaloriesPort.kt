package org.healthapp.app.port.input

import org.healthapp.infrastructure.dto.Response

interface GetUserCaloriesPort {
    fun getUserCalories(userId: Long, from: String, to: String): Response
}