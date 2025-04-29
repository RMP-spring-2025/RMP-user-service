package org.healthapp.infrastructure.adapter.input.interfaces

interface KeyDBInputPort {
    suspend fun receiveRequest(): String?

    fun receiveExternalResponse(): String?
}