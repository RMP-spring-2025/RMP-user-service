package org.healthapp.app.service

import org.healthapp.app.domain.UserStat
import org.healthapp.app.port.input.GetUserStatPort
import org.healthapp.app.port.output.UserDataRepository
import java.util.*

class GetUserStatService(val userDataRepository: UserDataRepository) : GetUserStatPort {
    override fun getUserStat(userId: UUID): UserStat? {
        return userDataRepository.getUserStatistic(userId)
    }

}