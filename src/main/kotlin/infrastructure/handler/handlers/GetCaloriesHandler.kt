package org.healthapp.infrastructure.handler.handlers

import org.healthapp.app.port.input.GetUserCaloriesPort
import org.healthapp.infrastructure.adapter.output.ResponseProcessor
import org.healthapp.infrastructure.dto.Response
import org.healthapp.infrastructure.handler.interfaces.RequestHandler
import org.healthapp.infrastructure.request.Request

class GetCaloriesHandler(
    private val getUserCaloriesPort: GetUserCaloriesPort,
    private val outPort: ResponseProcessor
) : RequestHandler {
    override fun handle(request: Request) {
        request as Request.GetCaloriesRequest
        outPort.sendResponse(
            Response.CaloriesResponse(
                request.requestId,
                getUserCaloriesPort.getUserCalories(request.userId, request.from, request.to)
            )
        )
    }
}