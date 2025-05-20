package org.healthapp.infrastructure.handler.handlers

import org.healthapp.app.domain.ProductConsumption
import org.healthapp.app.port.input.GetUserIdsPort
import org.healthapp.app.port.input.GetUserStatPort
import org.healthapp.infrastructure.adapter.output.interfaces.KeyDBOutputPort
import org.healthapp.infrastructure.handler.interfaces.RequestHandler
import org.healthapp.infrastructure.request.Request
import org.healthapp.infrastructure.response.Response

class GetUserStatHandler(
    private val outPort: KeyDBOutputPort,
    private val getUserStatPort: GetUserStatPort

): RequestHandler {
    override suspend fun handle(request: Request) {
        request as Request.GetUserStatisticRequest
        val res = getUserStatPort.getUserStat(
            request.userId
        )
        if (res != null) {
            outPort.sendResponse(Response.UserStatResponse(request.requestId, res.userId, res.username, res.weight, res.height, res.age, res.goal, res.sex))
        } else {
            outPort.sendResponse(Response.FailureResponse(request.requestId, "Get user statistic failed"))
        }
    }
}