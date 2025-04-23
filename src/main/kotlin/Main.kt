package org.healthapp

import ResponseAwaiter
import app.service.AddProductConsumptionService
import app.service.GetUserCaloriesService
import org.healthapp.app.port.input.AddProductConsumptionPort
import org.healthapp.app.port.input.GetUserCaloriesPort
import org.healthapp.app.port.output.UserProductRepository
import org.healthapp.infrastructure.adapter.input.KeyDBInputAdapter
import org.healthapp.infrastructure.adapter.input.RequestProcessor
import org.healthapp.infrastructure.adapter.output.KeyDBOutputAdapter
import org.healthapp.infrastructure.adapter.output.ResponseProcessor
import org.healthapp.infrastructure.adapter.output.UserProductRepositoryImpl
import org.healthapp.infrastructure.handler.DefaultHandleRegistry
import org.healthapp.infrastructure.handler.handlers.AddProductConsumptionHandler
import org.healthapp.infrastructure.handler.handlers.GetCaloriesHandler
import org.healthapp.infrastructure.handler.interfaces.RequestHandler
import org.healthapp.util.KeyDBConnection

fun main() {

    val connection = KeyDBConnection()
    val responseAwaiter = ResponseAwaiter()
    val outputAdapter = KeyDBOutputAdapter(connection)
    val outPort = ResponseProcessor(outputAdapter)
    val userProductRepository: UserProductRepository = UserProductRepositoryImpl()
    val addProductConsumptionService: AddProductConsumptionPort = AddProductConsumptionService(userProductRepository)
    val getCaloriesService: GetUserCaloriesPort = GetUserCaloriesService(userProductRepository)

    val handlers: Map<String, RequestHandler> = mapOf(
        "add_product" to AddProductConsumptionHandler(addProductConsumptionService, outPort),
        "get_calories" to GetCaloriesHandler(getCaloriesService, outputAdapter, responseAwaiter)
    )


    val handlerRegistry = DefaultHandleRegistry(handlers)

    val input = RequestProcessor(KeyDBInputAdapter(connection), handlerRegistry, responseAwaiter)
    input.startListening()
}
