package org.healthapp.infrastructure.adapter.output

import org.healthapp.app.domain.ProductConsumption
import org.healthapp.app.port.output.UserProductRepository
import org.healthapp.infrastructure.dto.ProductStatDTO
import org.healthapp.infrastructure.persistance.DatabaseConfiguration
import org.healthapp.infrastructure.persistance.Queries
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID




class UserProductRepositoryImpl: UserProductRepository {
    override fun saveUserConsumedProduct(consumedProduct: ProductConsumption): Boolean {
        return try{
            DatabaseConfiguration.getConnection().use { connection ->
                val statement = connection.prepareStatement(Queries.SAVE_USER_CONSUMED_PRODUCT.query.trimIndent())
                statement.setObject(1, consumedProduct.user.id)
                statement.setLong(2, consumedProduct.productId)
                statement.setDouble(3, consumedProduct.mass)
                statement.setObject(4, consumedProduct.timeStamp)
                statement.executeUpdate() > 0
            }
        }
        catch (e: Exception){
            println("DataBase error: ${e.message}")
            false
        }

    }

    override fun getCaloriesFromTo(
        userId: UUID,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<ProductStatDTO> {
        return try {
            DatabaseConfiguration.getConnection().use { connection ->
                val statement = connection.prepareStatement(Queries.GET_CALORIES_FROM_TO.query.trimIndent())
                statement.setObject(1, userId)
                statement.setObject(2, from) // Прямая передача LocalDateTime
                statement.setObject(3, to)   // Прямая передача LocalDateTime
                statement.executeQuery().use { rs ->
                    val result = mutableListOf<ProductStatDTO>()
                    while (rs.next()) {
                        result.add(
                            ProductStatDTO(
                                productId = rs.getLong("product_id"),
                                time = rs.getObject("timestamp", LocalDateTime::class.java).toString(), // Получение LocalDateTime
                                massConsumed = rs.getDouble("mass_consumed")
                            )
                        )
                    }
                    result
                }
            }
        } catch (ex: Exception) {
            throw RuntimeException("Error fetching data: ${ex.message}", ex)
        }
    }
}