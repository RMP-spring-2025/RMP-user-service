package org.healthapp.app.port.input

import org.healthapp.app.domain.UserWeight
import java.time.LocalDateTime
import java.util.*

interface UserWeightPort {
    fun addUserWeightEntry(userWeight: UserWeight): Boolean
    fun userWeightStatistic(userId: UUID, from: LocalDateTime, to: LocalDateTime): List<UserWeight>
}