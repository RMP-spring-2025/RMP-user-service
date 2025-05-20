package org.healthapp.infrastructure.adapter.input

import ResponseAwaiter
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import mu.KotlinLogging
import org.healthapp.infrastructure.adapter.input.interfaces.KeyDBInputPort
import org.healthapp.infrastructure.handler.interfaces.HandleRegistry
import org.healthapp.infrastructure.request.Request
import org.healthapp.infrastructure.response.ExternalResponse
import org.healthapp.util.JsonSerializationConfig
private val logger = KotlinLogging.logger {  }
class RequestProcessor(
    private val keyDBPort: KeyDBInputPort,
    private val handlers: HandleRegistry,
    private val responseAwaiter: ResponseAwaiter
) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val requestChannel = Channel<String>(capacity = Channel.UNLIMITED)
    private val responseChannel = Channel<String>(capacity = Channel.UNLIMITED)

    fun startListening() {
        scope.launch {
            while (true) {
                val request = keyDBPort.receiveRequest() ?: continue
                logger.info("Received request from API gateway: $request")

                requestChannel.send(request)
            }
        }


        scope.launch {
            while (true) {
                val response = keyDBPort.receiveExternalResponse() ?: continue
                logger.info("Received response from product service: $response")

                responseChannel.send(response)
            }
        }


        repeat(4) {
            scope.launch {
                for (request in requestChannel) {
                    processRequest(request)
                }
            }
        }


        repeat(4) {
            scope.launch {
                for (response in responseChannel) {
                    processResponse(response)
                }
            }
        }
    }

    private suspend fun processRequest(request: String) {
        try {
            val parsedRequest = parseRequestType(request)
            if (parsedRequest != null) {
                val handler = handlers.getHandler(parsedRequest.requestType)
                if (handler != null) {
                    scope.launch {
                        handler.handle(parsedRequest)
                    }
                } else {
                    println("No handler found for request type: ${parsedRequest.requestType}")
                }
            }
        } catch (e: Exception) {
            println("Error parsing request $request: ${e.message}")
        }
    }

    private fun processResponse(response: String) {
        var correlationId: String? = null
        try {
            val parsedResponse = JsonSerializationConfig.json.decodeFromString<ExternalResponse>(response)
            correlationId = parsedResponse.requestId
            responseAwaiter.completeResponse(parsedResponse.requestId, response)
        } catch (e: Exception) {
            println("Error parsing response $response: ${e.message}")
            responseAwaiter.failResponse(correlationId, e)
        }
    }

    private fun parseRequestType(request: String): Request? {
        return try {
            JsonSerializationConfig.json.decodeFromString<Request>(request)
        } catch (e: Exception) {
            println("Failed to parse request: ${e.message}")
            null
        }
    }

    fun stop() {
        scope.cancel()
        requestChannel.close()
        responseChannel.close()
    }
}