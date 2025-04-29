package org.healthapp.app.port.input

import org.healthapp.app.domain.ProductConsumption

interface AddProductConsumptionPort {
    fun addUserConsumedProduct(consumedProduct: ProductConsumption): Boolean
}