package org.healthapp.app.domain

import java.time.LocalDateTime
import java.util.*

data class UserWeight(val userId: UUID, val weight: Double, val time: LocalDateTime)