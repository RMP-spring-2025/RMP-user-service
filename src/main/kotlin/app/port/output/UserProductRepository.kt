package org.healthapp.app.port.output

import org.healthapp.app.domain.ProductConsumption
import org.healthapp.app.service.ProductStat

interface UserProductRepository {
    fun saveUserConsumedProduct(consumedProduct: ProductConsumption): Boolean
    fun getStatsFromTo(userId: Long, from: String, to: String): List<ProductStat>
}