package org.healthapp.app.port.input

import org.healthapp.app.domain.CaloriesStat
import org.healthapp.app.domain.Product
import org.healthapp.app.domain.ProductStat

interface GetUserCaloriesPort {

    fun calculateCalories(productStats: List<ProductStat>, response: List<Product>): List<CaloriesStat>
}