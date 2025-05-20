package org.healthapp.infrastructure.handler.handlers

import org.healthapp.app.domain.User
import org.healthapp.app.domain.UserWeight
import org.healthapp.app.port.input.CreateUserPort
import org.healthapp.app.port.input.UserWeightPort
import org.healthapp.infrastructure.adapter.output.interfaces.KeyDBOutputPort
import org.healthapp.infrastructure.handler.interfaces.RequestHandler
import org.healthapp.infrastructure.request.Request
import org.healthapp.infrastructure.response.Response

class AddUserHandler(
    private val createUserPort: CreateUserPort,
    private val userWeightPort: UserWeightPort,
    private val outPort: KeyDBOutputPort
) : RequestHandler {
    override suspend fun handle(request: Request) {
        request as Request.AddUserRequest

        val res = createUserPort.createUser(
            User(
                id = request.userId,
                username = request.username,
                age = request.age,
                height = request.height,
                userGoal = request.goal,
                sex = request.sex
            )
        )

        val resWeight = userWeightPort.addUserWeightEntry(
            UserWeight(
                userId = request.userId,
                weight = request.weight,
                time = request.time
            )
        )

        if (res && resWeight) {
            outPort.sendResponse(Response.SuccessResponse(request.requestId, "Success"))
        } else {
            outPort.sendResponse(Response.FailureResponse(request.requestId, "Add user failed"))
        }
    }
}