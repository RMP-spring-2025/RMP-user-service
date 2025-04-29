package org.healthapp.app.service

import org.healthapp.app.domain.UserWeight
import org.healthapp.app.port.input.UserWeightPort
import org.healthapp.app.port.output.UserDataRepository
import java.time.LocalDateTime
import java.util.*

class UserWeightService(
    private val userDataRepository: UserDataRepository
) : UserWeightPort {
    override fun addUserWeightEntry(userWeight: UserWeight): Boolean {
        return userDataRepository.addWeight(userWeight)
    }

    override fun userWeightStatistic(
        userId: UUID,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<UserWeight> {
        return userDataRepository.getUserWeightFromTo(userId, from, to)
    }
}