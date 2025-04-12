package org.healthapp.app.port.output

import org.healthapp.app.domain.ProductConsumption

interface UserProductRepository {
    fun saveUserConsumedProduct(consumedProduct: ProductConsumption): Boolean
}