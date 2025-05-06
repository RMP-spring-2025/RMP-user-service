package org.healthapp.infrastructure.adapter.output

import ResponseAwaiter
import org.healthapp.app.domain.Product
import org.healthapp.infrastructure.adapter.output.interfaces.ExternalProductPort
import org.healthapp.infrastructure.adapter.output.interfaces.KeyDBOutputPort
import org.healthapp.infrastructure.request.ExternalRequest
import org.healthapp.infrastructure.response.ExternalResponse
import org.healthapp.util.JsonSerializationConfig
import java.util.*

class ExternalProductAdapter(
    private val outPort: KeyDBOutputPort,
    private val responseAwaiter: ResponseAwaiter
) : ExternalProductPort {

    override suspend fun getProducts(productIds: List<Long>): Result<List<Product>> {
        if (productIds.isEmpty()) return Result.success(emptyList())

        val request = generateGetProductByIDRequest(productIds)
        val correlationId = outPort.sendProductRequest(request)
        val response = responseAwaiter.awaitResponse(correlationId).await()

        return try {
            val externalResponse = JsonSerializationConfig.json.decodeFromString<ExternalResponse>(response)
            val products = externalResponse.data.map {
                Product(
                    id = it.productId,
                    name = it.name,
                    bPerHundredGrams = it.B,
                    zPerHundredGrams = it.Z,
                    uPerHundredGrams = it.U,
                    caloriesPerHundredGrams = it.calories
                )
            }
            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun generateGetProductByIDRequest(productIds: List<Long>): ExternalRequest {

        return ExternalRequest.GetProductById(UUID.randomUUID(), productIds)

    }
}
