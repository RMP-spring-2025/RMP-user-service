package org.healthapp

import ResponseAwaiter
import app.service.AddProductConsumptionService
import app.service.GetUserCaloriesService
import org.healthapp.app.port.input.*
import org.healthapp.app.port.output.UserDataRepository
import org.healthapp.app.port.output.UserProductRepository
import org.healthapp.app.service.*
import org.healthapp.infrastructure.adapter.input.KeyDBInputAdapter
import org.healthapp.infrastructure.adapter.input.RequestProcessor
import org.healthapp.infrastructure.adapter.output.ExternalProductAdapter
import org.healthapp.infrastructure.adapter.output.KeyDBOutputAdapter
import org.healthapp.infrastructure.adapter.output.ResponseProcessor
import org.healthapp.infrastructure.adapter.output.UserDataRepositoryImpl
import org.healthapp.infrastructure.adapter.output.UserProductRepositoryImpl
import org.healthapp.infrastructure.adapter.output.interfaces.ExternalProductPort
import org.healthapp.infrastructure.handler.DefaultHandleRegistry
import org.healthapp.infrastructure.handler.handlers.*
import org.healthapp.infrastructure.handler.interfaces.RequestHandler
import org.healthapp.infrastructure.persistance.LiquibaseRunner
import org.healthapp.util.KeyDBConnection
import java.util.*
import kotlin.random.Random

fun main() {
    LiquibaseRunner(System.getenv("rmp-user-service_DBChangelogFilePath") ?: "db/changelog/changelog-master.xml").runMigrations()
    val connection = KeyDBConnection()
    val responseAwaiter = ResponseAwaiter()
    val outputAdapter = KeyDBOutputAdapter(connection)
    val outPort = ResponseProcessor(outputAdapter)
    val userProductRepository: UserProductRepository = UserProductRepositoryImpl()
    val userDataRepository: UserDataRepository = UserDataRepositoryImpl()
    val addProductConsumptionService: AddProductConsumptionPort = AddProductConsumptionService(userProductRepository)
    val calculationService: CaloriesCalculationPort = CalculateCaloriesService()
    val getCaloriesService: GetUserCaloriesPort = GetUserCaloriesService(calculationService)
    val getUserStatService: GetUserStatPort = GetUserStatService(userDataRepository)
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
        "add_user" to AddUserHandler(addUserService, userWeightService, outputAdapter),
        "add_weight" to AddUserWeightHandler(userWeightService, outputAdapter),
        "get_weight" to GetUserWeightStatisticHandler(userWeightService, outputAdapter),
        "get_user_stat" to GetUserStatHandler(outputAdapter, getUserStatService)
    )


    val handlerRegistry = DefaultHandleRegistry(handlers)

    val input = RequestProcessor(KeyDBInputAdapter(connection), handlerRegistry, responseAwaiter)
    input.startListening()
    Thread.sleep(Long.MAX_VALUE)
}
