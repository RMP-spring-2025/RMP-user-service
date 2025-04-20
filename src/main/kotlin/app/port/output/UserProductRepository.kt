package org.healthapp.app.port.output

import org.healthapp.app.domain.ProductConsumption
import org.healthapp.infrastructure.dto.StatEntryDTO
import java.util.UUID

interface UserProductRepository {
    fun saveUserConsumedProduct(consumedProduct: ProductConsumption)
    fun getCaloriesFromTo(userId: UUID, from: String, to: String): List<StatEntryDTO>
}