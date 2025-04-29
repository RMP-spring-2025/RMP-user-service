package org.healthapp.infrastructure.handler.handlers

import org.healthapp.app.domain.User
import org.healthapp.app.port.input.CreateUserPort
import org.healthapp.infrastructure.adapter.output.interfaces.KeyDBOutputPort
import org.healthapp.infrastructure.handler.interfaces.RequestHandler
import org.healthapp.infrastructure.request.Request
import org.healthapp.infrastructure.response.Response

class AddUserHandler(
    private val createUserPort: CreateUserPort,
    private val outPort: KeyDBOutputPort
) : RequestHandler {
    override suspend fun handle(request: Request) {
        request as Request.AddUserRequest

        val res = createUserPort.createUser(
            User(
                id = request.userId,
                username = request.username,
                age = request.age,
                height = request.height
            )
        )

        if (res) {
            outPort.sendResponse(Response.SuccessResponse(request.requestId, "Success"))
        } else {
            outPort.sendResponse(Response.FailureResponse(request.requestId, "Add user failed"))
        }
    }
}