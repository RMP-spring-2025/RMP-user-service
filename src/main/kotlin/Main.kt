package org.healthapp

import ResponseAwaiter
import app.service.AddProductConsumptionService
import app.service.GetUserCaloriesService
import org.healthapp.app.port.input.AddProductConsumptionPort
import org.healthapp.app.port.input.CaloriesCalculationPort
import org.healthapp.app.port.input.CreateUserPort
import org.healthapp.app.port.input.GetUserCaloriesPort
import org.healthapp.app.port.input.GetUserIdsPort
import org.healthapp.app.port.input.UserWeightPort
import org.healthapp.app.port.output.UserDataRepository
import org.healthapp.app.port.output.UserProductRepository
import org.healthapp.app.service.AddUserService
import org.healthapp.app.service.CalculateCaloriesService
import org.healthapp.app.service.GetUserIdsService
import org.healthapp.app.service.UserWeightService
import org.healthapp.infrastructure.adapter.input.KeyDBInputAdapter
import org.healthapp.infrastructure.adapter.input.RequestProcessor
import org.healthapp.infrastructure.adapter.output.ExternalProductAdapter
import org.healthapp.infrastructure.adapter.output.KeyDBOutputAdapter
import org.healthapp.infrastructure.adapter.output.ResponseProcessor
import org.healthapp.infrastructure.adapter.output.UserDataRepositoryImpl
import org.healthapp.infrastructure.adapter.output.UserProductRepositoryImpl
import org.healthapp.infrastructure.adapter.output.interfaces.ExternalProductPort
import org.healthapp.infrastructure.handler.DefaultHandleRegistry
import org.healthapp.infrastructure.handler.handlers.AddProductConsumptionHandler
import org.healthapp.infrastructure.handler.handlers.AddUserHandler
import org.healthapp.infrastructure.handler.handlers.AddUserWeightHandler
import org.healthapp.infrastructure.handler.handlers.GetCaloriesHandler
import org.healthapp.infrastructure.handler.handlers.GetProductStatHandler
import org.healthapp.infrastructure.handler.handlers.GetUserWeightStatisticHandler
import org.healthapp.infrastructure.handler.interfaces.RequestHandler
import org.healthapp.infrastructure.persistance.LiquibaseRunner
import org.healthapp.util.KeyDBConnection

fun main() {
    LiquibaseRunner(System.getenv("rmp-user-service_DBChangelogFilePath")).runMigrations()

    val connection = KeyDBConnection()
    val responseAwaiter = ResponseAwaiter()
    val outputAdapter = KeyDBOutputAdapter(connection)
    val outPort = ResponseProcessor(outputAdapter)
    val userProductRepository: UserProductRepository = UserProductRepositoryImpl()
    val userDataRepository: UserDataRepository = UserDataRepositoryImpl()
    val addProductConsumptionService: AddProductConsumptionPort = AddProductConsumptionService(userProductRepository)
    val calculationService: CaloriesCalculationPort = CalculateCaloriesService()
    val getCaloriesService: GetUserCaloriesPort = GetUserCaloriesService(calculationService)
    val getUserIdsService: GetUserIdsPort = GetUserIdsService(userProductRepository)
    val addUserService: CreateUserPort = AddUserService(userDataRepository)
    val userWeightService: UserWeightPort = UserWeightService(userDataRepository)
    val externalProductPort: ExternalProductPort = ExternalProductAdapter(outputAdapter, responseAwaiter)


    val handlers: Map<String, RequestHandler> = mapOf(
        "add_product" to AddProductConsumptionHandler(addProductConsumptionService, outPort),
        "get_calories" to GetCaloriesHandler(getCaloriesService, getUserIdsService, outputAdapter, externalProductPort),
        "get_products" to GetProductStatHandler(
            getUserIdsService,
            outputAdapter,
            calculationService,
            externalProductPort
        ),
        "add_user" to AddUserHandler(addUserService, outputAdapter),
        "add_weight_statistic" to AddUserWeightHandler(userWeightService, outputAdapter),
        "get_weight_statistic" to GetUserWeightStatisticHandler(userWeightService, outputAdapter)
    )


    val handlerRegistry = DefaultHandleRegistry(handlers)

    val input = RequestProcessor(KeyDBInputAdapter(connection), handlerRegistry, responseAwaiter)
    input.startListening()
    Thread.sleep(Long.MAX_VALUE)
}
