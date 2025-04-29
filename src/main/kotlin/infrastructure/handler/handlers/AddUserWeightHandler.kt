package org.healthapp.infrastructure.handler.handlers

import org.healthapp.app.domain.UserWeight
import org.healthapp.app.port.input.UserWeightPort
import org.healthapp.infrastructure.adapter.output.interfaces.KeyDBOutputPort
import org.healthapp.infrastructure.handler.interfaces.RequestHandler
import org.healthapp.infrastructure.request.Request
import org.healthapp.infrastructure.response.Response

class AddUserWeightHandler(
    private val userWeightPort: UserWeightPort,
    private val outPort: KeyDBOutputPort
) : RequestHandler {
    override suspend fun handle(request: Request) {
        request as Request.AddWeightRequest

        val res = userWeightPort.addUserWeightEntry(
            UserWeight(
                userId = request.userId,
                weight = request.weight,
                time = request.time
            )
        )

        if (res) {
            outPort.sendResponse(Response.SuccessResponse(request.requestId, "Success"))
        } else {
            outPort.sendResponse(Response.FailureResponse(request.requestId, "Add user weight failed"))
        }
    }
}