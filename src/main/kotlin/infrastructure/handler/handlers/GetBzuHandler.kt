package org.healthapp.infrastructure.handler.handlers

import org.healthapp.app.port.input.GetUserBzuPort
import org.healthapp.app.port.input.GetUserIdsPort
import org.healthapp.infrastructure.adapter.output.interfaces.ExternalProductPort
import org.healthapp.infrastructure.adapter.output.interfaces.KeyDBOutputPort
import org.healthapp.infrastructure.handler.interfaces.RequestHandler
import org.healthapp.infrastructure.request.Request
import org.healthapp.infrastructure.response.Response
import org.healthapp.infrastructure.response.StatEntryDTO

class GetBzuHandler(
    private val getUserBzuPort: GetUserBzuPort,
    private val getUserIdsPort: GetUserIdsPort,
    private val outPort: KeyDBOutputPort,
    private val externalProductPort: ExternalProductPort
): RequestHandler {
    override suspend fun handle(request: Request) {
        request as Request.GetBzuRequest

        val productStat = getUserIdsPort.getUserProductIds(request.userId, request.from, request.to)

        val productResults = externalProductPort.getProducts(productStat.map { it.productId }.distinct())

        productResults.fold(
            onSuccess = { products ->
                val bzuStat = getUserBzuPort.calculateBzu(productStat, products)
                val response = Response.StatisticResponse(
                    requestId = request.requestId,
                    stats = bzuStat.map { StatEntryDTO.BzuStats(it.time.toString(), it.B, it.Z, it.U) }
                )
                outPort.sendResponse(response)
            },
            onFailure = {}
        )

    }
}