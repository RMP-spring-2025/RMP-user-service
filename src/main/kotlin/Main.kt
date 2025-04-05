package org.healthapp

import org.healthapp.app.port.input.AddProductConsumptionPort
import app.service.AddProductConsumptionService
import org.healthapp.infrastructure.adapter.input.FakeKeyDBPort
import org.healthapp.infrastructure.adapter.input.KeyDBAdapter
import org.healthapp.infrastructure.adapter.output.UserProductInMemoryRepositoryImpl
import org.healthapp.infrastructure.handler.AddProductConsumptionHandler
import org.healthapp.infrastructure.handler.DefaultHandleRegistry
import org.healthapp.infrastructure.handler.HandleRegistry
import org.healthapp.infrastructure.handler.RequestHandler
import org.healthapp.infrastructure.persistance.DatabaseConfiguration

fun main() {
    val jsonAddProduct = """
        {
            "request_id": 123,
            "user_id": 42,
            "type": "add_product",
            "product_id": 123,
            "date": "2025-03-30",
            "mass_consumed": 12.0
        }
    """.trimIndent()
    val fakeKeyDB = FakeKeyDBPort()
    fakeKeyDB.addRequest(jsonAddProduct)

    val addProductConsumptionService: AddProductConsumptionPort = AddProductConsumptionService(UserProductInMemoryRepositoryImpl())
    val handlers: Map<String, RequestHandler> = mapOf(
        "add_product" to AddProductConsumptionHandler(addProductConsumptionService)
    )

    val handleRegistry: HandleRegistry = DefaultHandleRegistry(handlers)
    val adapter = KeyDBAdapter(fakeKeyDB, handleRegistry)
    adapter.startListening()


}

fun checkConnection(){
    val connection = DatabaseConfiguration.getConnection()

    val statement = connection.createStatement()
    val resultSet = statement.executeQuery("SELECT version()")

    while (resultSet.next()) {
        println("PostgreSQL version: ${resultSet.getString(1)}")
    }

    resultSet.close()
    statement.close()
    connection.close()
}