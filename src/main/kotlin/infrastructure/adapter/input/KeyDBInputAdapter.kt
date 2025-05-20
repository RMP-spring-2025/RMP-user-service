package org.healthapp.infrastructure.adapter.input

import org.healthapp.infrastructure.adapter.input.interfaces.KeyDBInputPort
import org.healthapp.util.KeyDBConnection

class KeyDBInputAdapter(
    private val connection: KeyDBConnection,
    private val requestQueue: String = "user_service_requests",
    private val productResponseQueue: String = "user_service_product_responses"
) : KeyDBInputPort {
    override suspend fun receiveRequest(): String? {
        val req = connection.withConnection { commands -> commands.brpop(1, requestQueue)?.value }
        return req
    }

    override suspend fun receiveExternalResponse(): String? {
        val res = connection.withConnection { commands -> commands.brpop(1, productResponseQueue)?.value  }
        return res
    }
}