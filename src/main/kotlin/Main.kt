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
    val getCalReq = "{\"requestType\":\"get_calories\",\"requestId\":\"3b762ffd-8a56-4a9e-902a-2b0e3803a485\",\"userId\":\"d39fb70b-2477-48e1-bc49-2bdbf742a12d\",\"from\":[2025,4,24,20,0],\"to\":[2025,4,24,21,20,20]}".trimIndent()
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
    outputAdapter.sendRequest(getCalReq)
    outputAdapter.sendRequest(getCalReq)
    val input = RequestProcessor(KeyDBInputAdapter(connection), handlerRegistry, responseAwaiter)
    input.startListening()
}
