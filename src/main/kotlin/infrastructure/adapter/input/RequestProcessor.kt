package org.healthapp.infrastructure.adapter.input

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.healthapp.infrastructure.adapter.input.interfaces.KeyDBInputPort
import org.healthapp.infrastructure.handler.ResponseAwaiter
import org.healthapp.infrastructure.handler.interfaces.HandleRegistry
import org.healthapp.infrastructure.request.Request
import org.healthapp.infrastructure.response.ExternalResponse
import org.healthapp.util.JsonSerializationConfig
import java.util.UUID

class RequestProcessor(private val keyDBPort: KeyDBInputPort, private val handlers: HandleRegistry, private val responseAwaiter: ResponseAwaiter) {
    fun startListening() = runBlocking {
        launch {
            while (true) {
                val request = keyDBPort.receiveRequest() ?: continue
                println(request)
                try {
                    val parsedRequest = parseRequestType(request)
                    println(parsedRequest)
                    if (parsedRequest != null) {
                        handlers.getHandler(parsedRequest.requestType)?.handle(parsedRequest)
                    }
                } catch (e: Exception) {
                    println("error parsing request $request")
                }
            }
        }

        launch {
            while (true){
                val response = keyDBPort.receiveExternalResponse() ?: continue
                println(response)
                var correlationId: String? = null
                try {
                    val parsedResponse = JsonSerializationConfig.json.decodeFromString<ExternalResponse>(response)
                    correlationId = parsedResponse.requestId
                    responseAwaiter.completeResponse(parsedResponse.requestId, response)
                } catch (e: Exception){
                    println("error parsing response $response")
                    responseAwaiter.failResponse(correlationId, e)
                }
            }
        }
    }

    private fun parseRequestType(request: String): Request? {
        return try {
            JsonSerializationConfig.json.decodeFromString<Request>(request)
        } catch (e: Exception) {
            println(e.printStackTrace())
            null
        }
    }
}

