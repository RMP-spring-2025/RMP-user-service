package org.healthapp.infrastructure.handler.handlers

import org.healthapp.app.port.input.AddProductConsumptionPort
import org.healthapp.infrastructure.dto.ProductConsumptionDto
import org.healthapp.infrastructure.dto.Response

import org.healthapp.infrastructure.handler.interfaces.RequestHandler
import org.healthapp.infrastructure.request.Request

class AddProductConsumptionHandler(private val addProductConsumptionPort: AddProductConsumptionPort) : RequestHandler {
    override fun handle(request: Request): Response {
        request as Request.AddProductRequest
        addProductConsumptionPort.addUserConsumedProduct(
            ProductConsumptionDto(
                request.requestId,
                request.userId,
                request.productId,
                request.massConsumed,
                request.date
            )
        )
        return Response.SuccessResponse(1L, "Success")
    }
}