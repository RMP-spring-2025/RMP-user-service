package org.healthapp.app.port.input

interface GetUserStatsPort {

    fun getUserStats(userId: Long, from: String?, to: String?)
}