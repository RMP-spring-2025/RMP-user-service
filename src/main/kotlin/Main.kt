package org.healthapp

import org.healthapp.app.port.input.AddProductConsumptionPort
import app.service.AddProductConsumptionService
import app.service.GetUserCaloriesService
import org.healthapp.app.port.input.GetUserCaloriesPort
import org.healthapp.app.port.output.UserProductRepository
import org.healthapp.infrastructure.adapter.input.KeyDBInputAdapter
import org.healthapp.infrastructure.adapter.input.RequestProcessor
import org.healthapp.infrastructure.adapter.output.KeyDBOutputAdapter
import org.healthapp.infrastructure.adapter.output.ResponseProcessor
import org.healthapp.infrastructure.adapter.output.UserProductRepositoryImpl
import org.healthapp.infrastructure.handler.DefaultHandleRegistry
import org.healthapp.infrastructure.handler.ResponseAwaiter
import org.healthapp.infrastructure.handler.handlers.AddProductConsumptionHandler
import org.healthapp.infrastructure.handler.handlers.GetCaloriesHandler
import org.healthapp.infrastructure.handler.interfaces.RequestHandler
import org.healthapp.infrastructure.persistance.DatabaseConfiguration
import org.healthapp.infrastructure.request.Request
import org.healthapp.infrastructure.response.Response
import org.healthapp.util.JsonSerializationConfig
import org.healthapp.util.KeyDBConnection
import java.time.LocalDateTime
import java.util.UUID
import kotlin.reflect.typeOf

fun main() {
    saveProductTest()
}

fun saveProductTest(){
    val reqId = UUID.randomUUID()

    val connection = KeyDBConnection()
    val responseAwaiter: ResponseAwaiter = ResponseAwaiter()
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