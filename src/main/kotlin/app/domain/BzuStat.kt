package org.healthapp.app.domain

import java.time.LocalDateTime

data class BzuStat(
    val time: LocalDateTime,
    val B: Int,
    val Z: Int,
    val U: Int
)