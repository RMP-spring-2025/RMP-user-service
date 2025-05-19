package org.healthapp.app.port.output

import org.healthapp.app.domain.User
import org.healthapp.app.domain.UserStat
import org.healthapp.app.domain.UserWeight
import java.time.LocalDateTime
import java.util.*

interface UserDataRepository {
    fun createUser(user: User): Boolean
    fun getUserStatistic(userId: UUID): UserStat?
    fun addWeight(userWeight: UserWeight): Boolean
    fun getUserWeightFromTo(userId: UUID, from: LocalDateTime, to: LocalDateTime): List<UserWeight>
}