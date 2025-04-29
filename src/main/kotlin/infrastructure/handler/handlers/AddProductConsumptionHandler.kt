package org.healthapp.infrastructure.handler.handlers

import org.healthapp.app.domain.ProductConsumption
import org.healthapp.app.port.input.AddProductConsumptionPort
import org.healthapp.infrastructure.adapter.output.ResponseProcessor
import org.healthapp.infrastructure.handler.interfaces.RequestHandler
import org.healthapp.infrastructure.request.Request
import org.healthapp.infrastructure.response.Response

class AddProductConsumptionHandler(
    private val addProductConsumptionPort: AddProductConsumptionPort,
    private val outPort: ResponseProcessor
) : RequestHandler {

    override suspend fun handle(request: Request) {
        request as Request.AddProductRequest
        val res = addProductConsumptionPort.addUserConsumedProduct(
            ProductConsumption(
                request.userId,
                request.productId,
                request.massConsumed,
                request.time
            )
        )
        if (res) {
            outPort.sendResponse(Response.SuccessResponse(request.requestId, "Success"))
        } else {
            outPort.sendResponse(Response.FailureResponse(request.requestId, "Add product failed"))
        }

    }
}