package org.healthapp.app.domain

import java.util.UUID

data class UserStat(
    val userId: UUID,
    val username: String,
    val weight: Double?,
    val height: Double,
    val age: Int,
    val goal: UserGoal,
    val sex: String
) {
}