package org.healthapp.infrastructure.adapter.output

import kotlinx.serialization.Serializable
import org.healthapp.app.domain.ProductConsumption
import org.healthapp.app.port.output.UserProductRepository
import org.healthapp.infrastructure.dto.StatEntryDTO
import java.util.UUID

@Serializable
data class Product(
    val id: Long,
    val name: String,
    val caloriesPer100g: Double
)

class MockProductRepository {
    private val mockProducts = listOf(
        Product(1, "Яблоко", 52.0),
        Product(2, "Курица", 239.0),
        Product(3, "Гречка", 343.0),
        Product(4, "Some", 531.0)
    )

    fun findById(id: Long): Product {
        return mockProducts.firstOrNull { it.id == id }
            ?: throw IllegalArgumentException("Product with id $id not found")
    }
}

class UserProductInMemoryRepositoryImpl : UserProductRepository {
    private val consumedProducts = mutableListOf<ProductConsumption>()

    override fun saveUserConsumedProduct(consumedProduct: ProductConsumption) {
        consumedProducts.add(consumedProduct)
        println(consumedProduct)
    }

    override fun getCaloriesFromTo(userId: UUID, from: String, to: String): List<StatEntryDTO> {
        val dailyConsumptions = consumedProducts.filter { it.timeStamp in from..to }.groupBy { it.timeStamp }

        val stats = dailyConsumptions.map { (date, dailyProduct) ->
            val totalCalories = dailyProduct.sumOf { consumption ->
                val product = MockProductRepository().findById(consumption.productId)
                (consumption.mass * product.caloriesPer100g / 100).toInt()
            }
            StatEntryDTO.CaloriesStat(
                time = date,
                calories = totalCalories
            )
        }.sortedBy { it.time }




        return stats
    }

}