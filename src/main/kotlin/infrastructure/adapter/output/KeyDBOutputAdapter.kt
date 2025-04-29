package org.healthapp.infrastructure.adapter.output

import org.healthapp.infrastructure.adapter.output.interfaces.KeyDBOutputPort
import org.healthapp.infrastructure.request.ExternalRequest
import org.healthapp.infrastructure.response.Response
import org.healthapp.util.ExternalJsonSerializationConfig
import org.healthapp.util.JsonSerializationConfig
import org.healthapp.util.KeyDBConnection

class KeyDBOutputAdapter(
    private val connection: KeyDBConnection,
    private val responseQueue: String = "user_service_response",
    private val requestQueue: String = "user_request_list",
    private val productServiceQueue: String = "user_service_product_requests"
) : KeyDBOutputPort {
    override suspend fun sendResponse(response: Response) {
        val jsonString = JsonSerializationConfig.json.encodeToString(response)
        println(jsonString)
        connection.commands.lpush(responseQueue, jsonString)
    }

    override fun sendRequest(request: String) {
        connection.commands.lpush(requestQueue, request)
    }

    override suspend fun sendProductRequest(externalRequest: ExternalRequest): String {
        try {
            val jsonString = ExternalJsonSerializationConfig.json.encodeToString(externalRequest)
            connection.commands.lpush(productServiceQueue, jsonString)
            println(jsonString)
            return externalRequest.requestId.toString()
        } catch (e: Exception) {
            println("Error sending product request: ${e.message}")
        }
        return "ds"
    }
}