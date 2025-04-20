package org.healthapp.infrastructure.adapter.input

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.healthapp.infrastructure.adapter.input.interfaces.KeyDBInputPort
import org.healthapp.infrastructure.handler.interfaces.HandleRegistry
import org.healthapp.infrastructure.request.Request
import org.healthapp.util.JsonSerializationConfig

class RequestProcessor(private val keyDBPort: KeyDBInputPort, private val handlers: HandleRegistry) {
    fun startListening() = runBlocking {
        launch {
            while (true) {
                val request = keyDBPort.receiveRequest() ?: continue
                try {
                    val parsedRequest = parseRequestType(request)
                    if (parsedRequest != null) {
                        handlers.getHandler(parsedRequest.requestType)?.handle(parsedRequest)
                    }
                } catch (e: Exception) {
                    println("error parsing request $request")
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

