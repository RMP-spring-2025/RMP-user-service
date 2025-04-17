package org.healthapp.app.port.output

import org.healthapp.app.domain.ProductConsumption
import org.healthapp.infrastructure.dto.RequestProductServiceDTO

interface UserProductRepository {
    fun saveUserConsumedProduct(consumedProduct: ProductConsumption): Boolean
    fun getCaloriesFromTo(userId: Long, from: String, to: String): List<RequestProductServiceDTO>
}