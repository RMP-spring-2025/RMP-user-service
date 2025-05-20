package org.healthapp.infrastructure.adapter.output

import io.lettuce.core.RedisClient
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.sync.RedisCommands
import mu.KotlinLogging
import org.healthapp.infrastructure.adapter.output.interfaces.KeyDBOutputPort
import org.healthapp.infrastructure.request.ExternalRequest
import org.healthapp.infrastructure.response.Response
import org.healthapp.util.ExternalJsonSerializationConfig
import org.healthapp.util.JsonSerializationConfig
import org.healthapp.util.KeyDBConnection

class KeyDBOutputAdapter(
    private val connection: KeyDBConnection,
    private val responseQueue: String = "user_service_response",
    private val requestQueue: String = "user_service_requests",
    private val productServiceQueue: String = "user_service_product_requests"
) : KeyDBOutputPort {
    private val logger = KotlinLogging.logger {  }

    override suspend fun sendResponse(response: Response) {
        val jsonString = JsonSerializationConfig.json.encodeToString(response)
        logger.info("Sending response: $jsonString")
        connection.withConnection {
            commands -> commands.rpush(responseQueue, jsonString)
        }
//        commands.rpush(responseQueue, jsonString)
        logger.info("Sent Response: $jsonString")
    }

    override suspend fun sendRequest(request: String) {
        connection.withConnection { commands -> commands.lpush(requestQueue, request) }
    }

    override suspend fun sendProductRequest(externalRequest: ExternalRequest): String {
        try {
            val jsonString = ExternalJsonSerializationConfig.json.encodeToString(externalRequest)
            logger.info("Sending product request: $jsonString")
            connection.withConnection { commands ->commands.lpush(productServiceQueue, jsonString) }
            return externalRequest.requestId.toString()
        } catch (e: Exception) {
            println("Error sending product request: ${e.message}")
        }
        return "ds"
    }
}