package org.healthapp.app.port.input

import org.healthapp.app.domain.UserStat
import java.util.UUID

interface GetUserStatPort {

    fun getUserStat(userId: UUID): UserStat?
}