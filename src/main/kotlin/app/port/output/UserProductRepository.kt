package org.healthapp.app.port.output

import org.healthapp.app.domain.ProductConsumption
import org.healthapp.app.domain.ProductStat
import java.time.LocalDateTime
import java.util.*

interface UserProductRepository {
    fun saveUserConsumedProduct(consumedProduct: ProductConsumption): Boolean
    fun getUserProductIdsFromTo(userId: UUID, from: LocalDateTime, to: LocalDateTime): List<ProductStat>
}