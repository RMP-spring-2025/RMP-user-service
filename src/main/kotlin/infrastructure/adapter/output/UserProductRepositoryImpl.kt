package org.healthapp.infrastructure.adapter.output

import org.healthapp.app.domain.ProductConsumption
import org.healthapp.app.domain.ProductStat
import org.healthapp.app.port.output.UserProductRepository
import org.healthapp.infrastructure.persistance.DatabaseConfiguration
import org.healthapp.infrastructure.persistance.Queries
import java.time.LocalDateTime
import java.util.*


class UserProductRepositoryImpl : UserProductRepository {
    override fun saveUserConsumedProduct(consumedProduct: ProductConsumption): Boolean {
        return try {
            DatabaseConfiguration.getConnection().use { connection ->
                val statement = connection.prepareStatement(Queries.SAVE_USER_CONSUMED_PRODUCT.query.trimIndent())
                statement.setObject(1, consumedProduct.user)
                statement.setLong(2, consumedProduct.productId)
                statement.setDouble(3, consumedProduct.mass)
                statement.setObject(4, consumedProduct.timeStamp)
                statement.executeUpdate() > 0
            }
        } catch (e: Exception) {
            println("DataBase error: ${e.message}")
            false
        }

    }

    override fun getUserProductIdsFromTo(
        userId: UUID,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<ProductStat> {
        return try {
            DatabaseConfiguration.getConnection().use { connection ->
                val statement = connection.prepareStatement(Queries.GET_PRODUCTS_FROM_TO.query.trimIndent())
                statement.setObject(1, userId)
                statement.setObject(2, from)
                statement.setObject(3, to)
                statement.executeQuery().use { rs ->
                    val result = mutableListOf<ProductStat>()
                    while (rs.next()) {
                        result.add(
                            ProductStat(
                                productId = rs.getLong("product_id"),
                                time = rs.getObject("timestamp", LocalDateTime::class.java), // Получение LocalDateTime
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