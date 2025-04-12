package org.healthapp.infrastructure.adapter.output

import org.healthapp.app.domain.ProductConsumption
import org.healthapp.app.port.output.UserProductRepository
import org.healthapp.infrastructure.persistance.DatabaseConfiguration
import java.sql.Statement
import java.sql.Timestamp

class UserProductInMemoryRepositoryImpl: UserProductRepository {
    override fun saveUserConsumedProduct(consumedProduct: ProductConsumption): Boolean {
        return try{
            val sql = """
            INSERT INTO user_products(user_id, product_id, mass_consumed, timestamp)
            VALUES (?, ?, ?, ?)
            """.trimIndent()
            DatabaseConfiguration.getConnection().use { connection ->
                val statement = connection.prepareStatement(sql)
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
}