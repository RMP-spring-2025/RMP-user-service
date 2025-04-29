package app.service

import org.healthapp.app.domain.CaloriesStat
import org.healthapp.app.domain.Product
import org.healthapp.app.domain.ProductStat
import org.healthapp.app.port.input.CaloriesCalculationPort
import org.healthapp.app.port.input.GetUserCaloriesPort


class GetUserCaloriesService(val caloriesCalculationPort: CaloriesCalculationPort) : GetUserCaloriesPort {
    override fun calculateCalories(
        productStats: List<ProductStat>,
        response: List<Product>
    ): List<CaloriesStat> {
        val productMap = response.associateBy { it.id }

        return productStats.mapNotNull { stat ->
            val product = productMap[stat.productId] ?: return@mapNotNull null
            val calories = caloriesCalculationPort.calculateCalories(stat.massConsumed, product.caloriesPerHundredGrams)
            if (calories < 0) null
            else CaloriesStat(time = stat.time, calories = calories.toInt())
        }
    }
}