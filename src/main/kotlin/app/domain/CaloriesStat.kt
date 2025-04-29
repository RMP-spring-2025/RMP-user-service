package org.healthapp.app.domain

import java.time.LocalDateTime

data class CaloriesStat(
    val time: LocalDateTime,
    val calories: Int
)