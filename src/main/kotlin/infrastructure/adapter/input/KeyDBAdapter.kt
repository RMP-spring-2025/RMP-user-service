package org.healthapp.infrastructure.adapter.input

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.healthapp.infrastructure.handler.HandleRegistry
import org.healthapp.infrastructure.request.Request

class KeyDBAdapter(private val keyDBPort: KeyDBPort, private val handlers: HandleRegistry) {
    fun startListening() = runBlocking {
        launch {
            while (true){
                val request = keyDBPort.receiveRequest() ?: continue
                try {
                    val parsedRequest = parseRequestType(request)
                    if (parsedRequest != null){
                        val handler = handlers.getHandler(parsedRequest.type)
                        handler?.handle(parsedRequest)
                    }
                } catch (e: Exception){
                    println("error parsing request $request")
                }
            }
        }
    }

    private fun parseRequestType(request:String): Request?{
        return try {
            println(Json.decodeFromString<Request>(request).toString())
            Json.decodeFromString<Request>(request)
        } catch (e: Exception){
            println("Parse Json request error $request")
            null
        }
    }
}

