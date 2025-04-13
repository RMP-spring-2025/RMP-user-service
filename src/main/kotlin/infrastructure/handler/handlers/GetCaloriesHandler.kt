package org.healthapp.infrastructure.handler.handlers

import org.healthapp.app.port.input.GetUserCaloriesPort
import org.healthapp.infrastructure.dto.Response
import org.healthapp.infrastructure.handler.interfaces.RequestHandler
import org.healthapp.infrastructure.request.Request

class GetCaloriesHandler(private val getUserCaloriesPort: GetUserCaloriesPort) : RequestHandler {
    override fun handle(request: Request): Response {
        request as Request.GetCaloriesRequest
        return getUserCaloriesPort.getUserCalories(request.userId, request.from, request.to)
    }
}