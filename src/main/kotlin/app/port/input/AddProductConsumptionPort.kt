package org.healthapp.app.port.input

import org.healthapp.infrastructure.dto.ProductConsumptionDTO

interface AddProductConsumptionPort {
    fun addUserConsumedProduct(consumedProduct: ProductConsumptionDTO) : Boolean
}