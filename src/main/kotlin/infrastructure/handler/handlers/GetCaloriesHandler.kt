package org.healthapp.infrastructure.handler.handlers

import kotlinx.coroutines.time.withTimeoutOrNull
import org.healthapp.app.port.input.GetUserCaloriesPort
import org.healthapp.infrastructure.adapter.output.ResponseProcessor
import org.healthapp.infrastructure.adapter.output.interfaces.KeyDBOutputPort
import org.healthapp.infrastructure.dto.ProductStatDTO
import org.healthapp.infrastructure.response.Response
import org.healthapp.infrastructure.handler.ResponseAwaiter
import org.healthapp.infrastructure.handler.interfaces.RequestHandler
import org.healthapp.infrastructure.request.ExternalRequest
import org.healthapp.infrastructure.request.Request
import org.healthapp.infrastructure.response.ExternalResponse
import org.healthapp.util.ExternalJsonSerializationConfig
import org.healthapp.util.JsonSerializationConfig
import java.time.Duration
import java.util.UUID

class GetCaloriesHandler(
    private val getUserCaloriesPort: GetUserCaloriesPort,
    private val outPort: KeyDBOutputPort,
    private val responseAwaiter: ResponseAwaiter
) : RequestHandler {
    override val requiresMicroservice: Boolean
        get() = true

    override suspend fun handle(request: Request) {
        request as Request.GetCaloriesRequest

        val productStatsDTO = getUserCaloriesPort.getUserCalories(request.userId, request.from, request.to)

        val request = generateGetProductByIDRequest(productStatsDTO)

        val correlationId = outPort.sendProductRequest( request)

        val productResponse = withTimeoutOrNull(Duration.ofSeconds(5)) {
            responseAwaiter.awaitResponse(correlationId).await()
        }
        if (productResponse == null) {
            return
        }

        val externalResponse = try {
            JsonSerializationConfig.json.decodeFromString<ExternalResponse>(productResponse)
        } catch (e: Exception) {
            return
        }

        val caloriesResponse = getUserCaloriesPort.calculateCalories(productStatsDTO, externalResponse)

        outPort.sendResponse(Response.CaloriesResponse(request.requestId, caloriesResponse))


    }

    private fun generateGetProductByIDRequest(stats: List<ProductStatDTO>) : ExternalRequest{
        val productId = stats.map { it.productId }

        return ExternalRequest.GetProductById(UUID.randomUUID(), productId)
    }
}