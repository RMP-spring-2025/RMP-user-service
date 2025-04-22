package org.healthapp

import ResponseAwaiter
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
    val req = "{\"requestType\":\"get_calories\",\"requestId\":\"384cdda0-6127-408b-b049-eaba3a0375ff\",\"userId\":\"d39fb70b-2477-48e1-bc49-2bdbf742a12d\",\"from\":[2025,4,24,0,0],\"to\":[2025,4,24,21,20,20]}".trimIndent()
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