package org.healthapp.app.port.input

import org.healthapp.app.domain.User

interface CreateUserPort {
    fun createUser(user: User): Boolean
}