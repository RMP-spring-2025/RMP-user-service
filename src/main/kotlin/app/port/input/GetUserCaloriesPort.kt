package org.healthapp.app.port.input

interface GetUserCaloriesPort {
    fun getUserCalories(userId: Long, from: String?, to: String?)
}