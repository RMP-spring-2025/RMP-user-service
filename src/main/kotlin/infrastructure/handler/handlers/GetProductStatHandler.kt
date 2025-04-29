package org.healthapp.infrastructure.handler.handlers

import org.healthapp.app.port.input.CaloriesCalculationPort
import org.healthapp.app.port.input.GetUserIdsPort
import org.healthapp.infrastructure.adapter.output.interfaces.ExternalProductPort
import org.healthapp.infrastructure.adapter.output.interfaces.KeyDBOutputPort
import org.healthapp.infrastructure.handler.interfaces.RequestHandler
import org.healthapp.infrastructure.request.Request
import org.healthapp.infrastructure.response.Response
import org.healthapp.infrastructure.response.StatEntryDTO

class GetProductStatHandler(
    private val getUserIdsPort: GetUserIdsPort,
    private val outPort: KeyDBOutputPort,
    private val caloriesCalculationPort: CaloriesCalculationPort,
    private val externalProductPort: ExternalProductPort
) : RequestHandler {
    override suspend fun handle(request: Request) {
        request as Request.GetProductsRequest
        val productStats = getUserIdsPort.getUserProductIds(request.userId, request.from, request.to)

        val productsResult = externalProductPort.getProducts(productStats.map { it.productId }.distinct())

        productsResult.fold(
            onSuccess = { products ->
                val productMap = products.associateBy { it.id }
                val stats = productStats.map { stat ->
                    val product = productMap[stat.productId]
                    StatEntryDTO.ProductStat(
                        time = stat.time.toString(),
                        name = product?.name ?: "Unknown",
                        calories = caloriesCalculationPort.calculateCalories(
                            stat.massConsumed, product?.caloriesPerHundredGrams ?: 0.0
                        ).toInt()
                    )
                }
                outPort.sendResponse(Response.StatisticResponse(request.requestId, stats))

            },
            onFailure = {}
        )

    }
}