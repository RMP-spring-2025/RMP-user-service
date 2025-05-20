package org.healthapp.infrastructure.adapter.input.interfaces

interface KeyDBInputPort {
    suspend fun receiveRequest(): String?

    suspend fun receiveExternalResponse(): String?
}