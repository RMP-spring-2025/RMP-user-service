package org.healthapp.infrastructure.adapter.output

import org.healthapp.app.domain.ProductConsumption
import org.healthapp.app.port.output.UserProductRepository
import org.healthapp.infrastructure.dto.RequestProductServiceDTO
import org.healthapp.infrastructure.persistance.DatabaseConfiguration
import org.healthapp.infrastructure.persistance.Queries
import java.sql.Timestamp


class UserProductRepositoryImpl: UserProductRepository {
    override fun saveUserConsumedProduct(consumedProduct: ProductConsumption): Boolean {
        return try{
            DatabaseConfiguration.getConnection().use { connection ->
                val statement = connection.prepareStatement(Queries.SAVE_USER_CONSUMED_PRODUCT.query.trimIndent())
                statement.setLong(1, consumedProduct.user.id)
                statement.setLong(2, consumedProduct.productId)
                statement.setDouble(3, consumedProduct.mass)
                val timestamp = Timestamp.valueOf("${consumedProduct.timeStamp} 00:00:00")
                statement.setTimestamp(4, timestamp)
                statement.executeUpdate() > 0
            }
        }
        catch (e: Exception){
            println("DataBase error: ${e.message}")
            false
        }

    }

    override fun getCaloriesFromTo(userId: Long, from: String, to: String): List<RequestProductServiceDTO> {
        return try{
            val fromTimestamp = Timestamp.valueOf("$from 00:00:00")
            val toTimestamp = Timestamp.valueOf("$to 23:59:59")
            DatabaseConfiguration.getConnection().use {connection ->
                val statement = connection.prepareStatement(Queries.GET_CALORIES_FROM_TO.query.trimIndent())
                statement.setLong(1, userId)
                statement.setTimestamp(2, fromTimestamp)
                statement.setTimestamp(3, toTimestamp)
                statement.executeQuery().use { rs ->
                    val result = mutableListOf<RequestProductServiceDTO>()
                    while(rs.next()){
                        result.add(
                            RequestProductServiceDTO(
                                productId = rs.getLong("product_id"),
                                data = rs.getTimestamp("timestamp").toLocalDateTime().toLocalDate().toString(),
                                massConsumed = rs.getDouble("mass_consumed")
                            )
                        )
                    }
                    result
                }
            }

        }
        catch (ex: Exception){
            throw RuntimeException("Error fetching data: ${ex.message}", ex)
        }
    }

}