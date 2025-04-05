package org.healthapp.app.port.input

import org.healthapp.app.domain.ProductConsumption
import org.healthapp.infrastructure.dto.ProductConsumptionDto

interface AddProductConsumptionPort {
    fun addUserConsumedProduct(consumedProduct: ProductConsumptionDto)
}