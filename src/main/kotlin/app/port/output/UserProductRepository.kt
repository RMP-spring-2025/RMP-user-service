package org.healthapp.app.port.output

import org.healthapp.app.domain.ProductConsumption
import org.healthapp.infrastructure.dto.StatEntryDTO

interface UserProductRepository {
    fun saveUserConsumedProduct(consumedProduct: ProductConsumption)
    fun getCaloriesFromTo(userId: Long, from: String, to: String): List<StatEntryDTO>
}