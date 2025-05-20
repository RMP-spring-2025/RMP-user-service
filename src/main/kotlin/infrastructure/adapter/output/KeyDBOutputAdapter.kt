package org.healthapp.infrastructure.adapter.output

import KeyDBConnectionPool
import mu.KotlinLogging
import org.healthapp.infrastructure.adapter.output.interfaces.KeyDBOutputPort
import org.healthapp.infrastructure.request.ExternalRequest
import org.healthapp.infrastructure.response.Response
import org.healthapp.util.ExternalJsonSerializationConfig
import org.healthapp.util.JsonSerializationConfig
import org.healthapp.util.KeyDBConnection

class KeyDBOutputAdapter(
    private val connection: KeyDBConnection,
    private val connectionPool: KeyDBConnectionPool,
    private val responseQueue: String = "user_service_response",
    private val requestQueue: String = "user_service_requests",
    private val productServiceQueue: String = "user_service_product_requests"
) : KeyDBOutputPort {
    private val logger = KotlinLogging.logger {  }
    override suspend fun sendResponse(response: Response) {
        connectionPool.useConnection { conn ->
            val jsonString = JsonSerializationConfig.json.encodeToString(response)
            logger.info("Sending response: $jsonString")
            conn.commands.lpush(responseQueue, jsonString)
        }
    }

    override fun sendRequest(request: String) {
        connection.commands.lpush(requestQueue, request)
    }

    override suspend fun sendProductRequest(externalRequest: ExternalRequest): String {
        try {
            val jsonString = ExternalJsonSerializationConfig.json.encodeToString(externalRequest)
            logger.info("Sending product request: $jsonString")
            connection.commands.lpush(productServiceQueue, jsonString)
            return externalRequest.requestId.toString()
        } catch (e: Exception) {
            println("Error sending product request: ${e.message}")
        }
        return "ds"
    }
}