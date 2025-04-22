package org.healthapp.app.port.output

import org.healthapp.app.domain.ProductConsumption
import org.healthapp.infrastructure.dto.ProductStatDTO
import java.time.LocalDateTime
import java.util.UUID

interface UserProductRepository {
    fun saveUserConsumedProduct(consumedProduct: ProductConsumption) : Boolean
    fun getCaloriesFromTo(userId: UUID, from: LocalDateTime, to: LocalDateTime): List<ProductStatDTO>
}