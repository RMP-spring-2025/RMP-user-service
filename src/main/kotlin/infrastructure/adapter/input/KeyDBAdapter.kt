package org.healthapp.infrastructure.adapter.input

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.healthapp.infrastructure.adapter.input.interfaces.KeyDBPort
import org.healthapp.infrastructure.adapter.output.KeyDBOutputAdapter
import org.healthapp.infrastructure.handler.interfaces.HandleRegistry
import org.healthapp.infrastructure.request.Request
import org.healthapp.util.JsonSerializationConfig

class KeyDBAdapter(private val keyDBPort: KeyDBPort, private val handlers: HandleRegistry) {
    fun startListening() = runBlocking {
        launch {
            while (true) {
                val request = keyDBPort.receiveRequest() ?: continue
                try {
                    val parsedRequest = parseRequestType(request)
                    if (parsedRequest != null) {
                        val handler = handlers.getHandler(parsedRequest.requestType)
                        if (handler != null) {
                            KeyDBOutputAdapter(keyDBPort).sendResponse(handler.handle(parsedRequest))
                        }
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

