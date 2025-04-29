package org.healthapp.app.service

import org.healthapp.app.domain.User
import org.healthapp.app.port.input.CreateUserPort
import org.healthapp.app.port.output.UserDataRepository

class AddUserService(
    private val userDataRepository: UserDataRepository
) : CreateUserPort {
    override fun createUser(user: User): Boolean {
        return userDataRepository.createUser(user)
    }
}