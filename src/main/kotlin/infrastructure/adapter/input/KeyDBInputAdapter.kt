package org.healthapp.infrastructure.adapter.input

import org.healthapp.infrastructure.adapter.input.interfaces.KeyDBInputPort
import org.healthapp.util.KeyDBConnection

class KeyDBInputAdapter(
    private val connection: KeyDBConnection,
    private val requestQueue: String = "user_request_list",
) : KeyDBInputPort {
    override fun receiveRequest(): String? {
        val req =  connection.commands.brpop(1, requestQueue)?.value
        println(req)
        return req
    }
}