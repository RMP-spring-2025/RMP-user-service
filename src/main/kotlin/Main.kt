package org.healthapp

import ResponseAwaiter
import app.service.AddProductConsumptionService
import app.service.GetUserCaloriesService
import mu.KotlinLogging
import org.healthapp.app.port.input.*
import org.healthapp.app.port.output.UserDataRepository
import org.healthapp.app.port.output.UserProductRepository
import org.healthapp.app.service.*
import org.healthapp.infrastructure.adapter.input.KeyDBInputAdapter
import org.healthapp.infrastructure.adapter.input.RequestProcessor
import org.healthapp.infrastructure.adapter.output.*
import org.healthapp.infrastructure.adapter.output.interfaces.ExternalProductPort
import org.healthapp.infrastructure.handler.DefaultHandleRegistry
import org.healthapp.infrastructure.handler.handlers.*
import org.healthapp.infrastructure.handler.interfaces.RequestHandler
import org.healthapp.util.KeyDBConnection
import java.util.*
import kotlin.random.Random

fun main() {
    val addProductReq = "{\"productId\":45,\"time\":\"2025-04-24T20:36:44\",\"massConsumed\":432543.0,\"requestType\":\"add_product\",\"requestId\":\"e17c5fb2-14d8-4bda-a094-bfb3a8e10d29\",\"userId\":\"d39fb70b-2477-48e1-bc49-2bdbf742a12d\"}".trimIndent()
    "{\"requestType\":\"get_calories\",\"requestId\":\"9569e7cd-5627-4423-97d8-2c36679e4e32\",\"userId\":\"d39fb70b-2477-48e1-bc49-2bdbf742a12d\",\"from\":[2025,4,24,20,0],\"to\":[2025,4,24,21,20,20]}".trimIndent()
    "{\"requestType\":\"get_products\",\"requestId\":\"fec2298a-6369-4e98-850d-18098ed5957b\",\"userId\":\"d39fb70b-2477-48e1-bc49-2bdbf742a12d\",\"from\":[2025,4,24,20,0],\"to\":[2025,4,24,21,20,20]}".trimIndent()
    """
        {
          "requestType": "add_user",
          "requestId": "${UUID.randomUUID()}",
          "userId": "d39fb70b-2477-48e1-bc49-2bdbf742a12d",
          "username": "John Doe",
          "age": 30,
          "height": 175
        }
    """.trimIndent()
    """
        {
            "requestType": "add_weight",
            "requestId": "${UUID.randomUUID()}",
            "userId": "d39fb70b-2477-48e1-bc49-2bdbf742a12d",
            "weight": ${Random.nextDouble()},
            "time": "2025-04-24T20:36:44"
        }
    """.trimIndent()
    val getWeightStatRequest = """
        {   
            "requestId": "${UUID.randomUUID()}",
            "userId": "d39fb70b-2477-48e1-bc49-2bdbf742a12d",
            "requestType": "get_weight",
            "from":[2025,4,24,20,0],
            "to":[2025,4,24,21,20,20]
        }
    """.trimIndent()
    val logger = KotlinLogging.logger {  }
    val connection = KeyDBConnection()
    val responseAwaiter = ResponseAwaiter()
    val outputAdapter = KeyDBOutputAdapter(connection)
    val outPort = ResponseProcessor(outputAdapter)
    val userProductRepository: UserProductRepository = UserProductRepositoryImpl()
    val userDataRepository: UserDataRepository = UserDataRepositoryImpl()
    val addProductConsumptionService: AddProductConsumptionPort = AddProductConsumptionService(userProductRepository)
    val calculationService: CaloriesCalculationPort = CalculateCaloriesService()
    val getCaloriesService: GetUserCaloriesPort = GetUserCaloriesService(calculationService)
    val getCalculateBzuService: BzuCalculationPort = CalculateBzuService()
    val getBzuService: GetUserBzuPort = GetUserBzuService(getCalculateBzuService)
    val getUserIdsService: GetUserIdsPort = GetUserIdsService(userProductRepository)
    val addUserService: CreateUserPort = AddUserService(userDataRepository)
    val userWeightService: UserWeightPort = UserWeightService(userDataRepository)
    val externalProductPort: ExternalProductPort = ExternalProductAdapter(outputAdapter, responseAwaiter)


    val handlers: Map<String, RequestHandler> = mapOf(
        "add_product" to AddProductConsumptionHandler(addProductConsumptionService, outPort),
        "get_calories" to GetCaloriesHandler(getCaloriesService, getUserIdsService, outputAdapter, externalProductPort),
        "get_bzu" to GetBzuHandler(getBzuService, getUserIdsService, outputAdapter, externalProductPort),
        "get_products" to GetProductStatHandler(
            getUserIdsService,
            outputAdapter,
            calculationService,
            externalProductPort,
            getCalculateBzuService
        ),
        "add_user" to AddUserHandler(addUserService, outputAdapter),
        "add_weight" to AddUserWeightHandler(userWeightService, outputAdapter),
        "get_weight" to GetUserWeightStatisticHandler(userWeightService, outputAdapter)
    )


    val handlerRegistry = DefaultHandleRegistry(handlers)
    outputAdapter.sendRequest(addProductReq)
    val input = RequestProcessor(KeyDBInputAdapter(connection), handlerRegistry, responseAwaiter)
    input.startListening()
    Thread.sleep(Long.MAX_VALUE)
}
