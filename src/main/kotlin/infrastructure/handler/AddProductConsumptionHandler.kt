package org.healthapp.infrastructure.handler

import org.healthapp.app.port.input.AddProductConsumptionPort
import org.healthapp.infrastructure.dto.ProductConsumptionDto
import org.healthapp.infrastructure.request.Request

class AddProductConsumptionHandler (private val addProductConsumptionPort: AddProductConsumptionPort): RequestHandler {
    override fun handle(request: Request): String {
        request as Request.AddProductRequest
        addProductConsumptionPort.addUserConsumedProduct(ProductConsumptionDto(request.userId, request.productId, request.massConsumed, request.date))
        return "Product consumption added successfully"
    }
}