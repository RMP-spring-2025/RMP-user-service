package org.healthapp.infrastructure.adapter.output

import org.healthapp.infrastructure.adapter.output.interfaces.KeyDBOutputPort
import org.healthapp.infrastructure.dto.Response
import org.healthapp.util.JsonSerializationConfig
import org.healthapp.util.KeyDBConnection

class KeyDBOutputAdapter(
    private val connection: KeyDBConnection,
    private val responseQueue: String = "user_response_list",
    private val requestQueue: String = "user_request_list",
) : KeyDBOutputPort {
    override fun sendResponse(response: Response) {
        val jsonString = JsonSerializationConfig.json.encodeToString(response)
        println(jsonString)
        connection.commands.lpush(responseQueue, jsonString)
    }

    override fun sendRequest(request: String) {
        connection.commands.lpush(requestQueue, request)
    }
}