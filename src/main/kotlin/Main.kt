package org.healthapp

import app.service.AddProductConsumptionService
import app.service.GetUserCaloriesService
import kotlinx.coroutines.*
import org.healthapp.app.port.input.AddProductConsumptionPort
import org.healthapp.app.port.input.GetUserCaloriesPort
import org.healthapp.infrastructure.adapter.input.KeyDBAdapter
import org.healthapp.infrastructure.adapter.input.KeyDBPortImpl
import org.healthapp.infrastructure.adapter.output.UserProductInMemoryRepositoryImpl
import org.healthapp.infrastructure.handler.DefaultHandleRegistry
import org.healthapp.infrastructure.handler.handlers.AddProductConsumptionHandler
import org.healthapp.infrastructure.handler.handlers.GetCaloriesHandler
import org.healthapp.infrastructure.handler.interfaces.HandleRegistry
import org.healthapp.infrastructure.handler.interfaces.RequestHandler
import java.time.LocalDate
import java.util.UUID
import kotlin.random.Random

fun main() {
    val scope = CoroutineScope(Dispatchers.Default + Job())
    val realKeyDb = KeyDBPortImpl()

    val job = scope.launch {
        sendPeriodicRequests(realKeyDb)
    }
    val inMemRep = UserProductInMemoryRepositoryImpl()
    val addProductConsumptionService: AddProductConsumptionPort = AddProductConsumptionService(inMemRep)
    val getCaloriesService: GetUserCaloriesPort = GetUserCaloriesService(inMemRep)
    val handlers: Map<String, RequestHandler> = mapOf(
        "add_product" to AddProductConsumptionHandler(addProductConsumptionService),
        "get_calories" to GetCaloriesHandler(getCaloriesService)
    )

    val handleRegistry: HandleRegistry = DefaultHandleRegistry(handlers)
    val adapter = KeyDBAdapter(realKeyDb, handleRegistry)
    adapter.startListening()
}

suspend fun sendPeriodicRequests(keyDb: KeyDBPortImpl) {

    var addProductCount = 0

    while (true) {
        var requestId = UUID.randomUUID()
        if (addProductCount < 2) {

            val newRequest = buildAddProductRequest(requestId)
            keyDb.sendRequest(newRequest)
            println("Sent add_product request with ID: $requestId")
            addProductCount++
        } else {

            val getCaloriesRequest = buildGetCaloriesRequest(requestId)
            keyDb.sendRequest(getCaloriesRequest)
            println("Sent get_calories request with ID: $requestId")
            addProductCount = 0
        }
        delay(1000)
    }
}

fun buildAddProductRequest(requestId: UUID): String {
    val currentDate = LocalDate.now().toString()
    val productId = Random.nextInt(1, 5)
    val massConsumed = Random.nextDouble(1.0, 100.0)

    return """
        {
            "request_id": $requestId,
            "user_id": 42,
            "requestType": "add_product",
            "product_id": $productId,
            "date": "$currentDate",
            "mass_consumed": $massConsumed
        }
    """.trimIndent()
}

fun buildGetCaloriesRequest(requestId: UUID): String {
    val fromDate = LocalDate.now().minusDays(0).toString()
    val toDate = LocalDate.now().toString()

    return """
    {
        "requestType": "get_calories",
        "request_id": $requestId,
        "user_id": 42,
        "from": "$fromDate",
        "to": "$toDate"
    }
""".trimIndent()
}