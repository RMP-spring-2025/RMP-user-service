package org.healthapp.infrastructure.adapter.input

import org.healthapp.infrastructure.adapter.input.interfaces.KeyDBInputPort
import org.healthapp.util.KeyDBConnection

class KeyDBInputAdapter(
    private val connection: KeyDBConnection,
    private val requestQueue: String = "user_service_requests",
    private val productResponseQueue: String = "user_service_response"
) : KeyDBInputPort {
    override fun receiveRequest(): String? {
        val req = connection.commands.brpop(1, requestQueue)?.value
        return req
    }

    override fun receiveExternalResponse(): String? {
        val res = connection.commands.brpop(1, productResponseQueue)?.value
        println("Received product response: $res")
        return res
    }
}