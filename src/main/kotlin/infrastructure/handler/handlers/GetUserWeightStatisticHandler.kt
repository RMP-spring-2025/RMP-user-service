package org.healthapp.infrastructure.handler.handlers

import org.healthapp.app.port.input.UserWeightPort
import org.healthapp.infrastructure.adapter.output.interfaces.KeyDBOutputPort
import org.healthapp.infrastructure.handler.interfaces.RequestHandler
import org.healthapp.infrastructure.request.Request
import org.healthapp.infrastructure.response.Response
import org.healthapp.infrastructure.response.StatEntryDTO

class GetUserWeightStatisticHandler(
    private val userWeightPort: UserWeightPort,
    private val outPort: KeyDBOutputPort
) : RequestHandler {
    override suspend fun handle(request: Request) {
        request as Request.GetWeightStatisticRequest

        val res = userWeightPort.userWeightStatistic(request.userId, request.from, request.to)

        val response = res.map { x -> StatEntryDTO.WeightStat(x.weight, x.time.toString()) }

        if (!res.isEmpty()) {
            outPort.sendResponse(Response.StatisticResponse(request.requestId, response))
        } else {
            outPort.sendResponse(Response.FailureResponse(request.requestId, "Get user weight statistic failed"))
        }
    }
}