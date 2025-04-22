package app.service

import org.healthapp.app.port.input.GetUserCaloriesPort
import org.healthapp.app.port.output.UserProductRepository
import org.healthapp.infrastructure.dto.ProductStatDTO
import org.healthapp.infrastructure.response.ExternalResponse
import org.healthapp.infrastructure.response.Product
import org.healthapp.infrastructure.response.Response
import org.healthapp.infrastructure.response.StatEntryDTO
import java.time.LocalDateTime
import java.util.UUID


class GetUserCaloriesService(private val userProductRepository: UserProductRepository) : GetUserCaloriesPort {
    override fun getUserCalories(userId: UUID, from: LocalDateTime, to: LocalDateTime): List<ProductStatDTO> {
        val stats = userProductRepository.getCaloriesFromTo(userId, from, to)
        return stats
    }

    override fun calculateCalories(
        productStats: List<ProductStatDTO>,
        response: ExternalResponse
    ) : List<StatEntryDTO> {
        val productMap = response.products.associateBy { it.productId }

        val stats = productStats.mapNotNull { stat ->
            val product = productMap[stat.productId]
            if (product == null) {
                println("Product with ID ${stat.productId} not found in ExternalResponse")
                return@mapNotNull null
            }

            val calories = calculateProductCalories(stat, product)
            if (calories < 0) {
                println("Invalid calories calculated for product ${stat.productId}: $calories")
                return@mapNotNull null
            }

            StatEntryDTO.CaloriesStat(
                time = stat.time,
                calories = calories
            )
        }
        return stats
    }

    private fun calculateProductCalories(stat: ProductStatDTO, product: Product): Int {
        // Проверяем, что масса потребления корректна
        if (stat.massConsumed <= 0) {
            return 0
        }

        // Если mass указан, предполагаем, что calories соответствуют этой массе
        return if (product.mass > 0) {
            // Калории на грамм = calories / mass
            val caloriesPerGram = product.calories.toDouble() / product.mass
            // Общие калории = масса потребления * калории на грамм
            (stat.massConsumed * caloriesPerGram).toInt()
        } else {
            // Если mass отсутствует, предполагаем, что calories указаны для 100 г
            val caloriesPer100Grams = product.calories
            // Общие калории = (масса потребления / 100) * калории на 100 г
            (stat.massConsumed / 100.0 * caloriesPer100Grams).toInt()
        }
    }
}