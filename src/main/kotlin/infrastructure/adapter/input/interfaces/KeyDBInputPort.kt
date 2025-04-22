package org.healthapp.infrastructure.adapter.input.interfaces

interface KeyDBInputPort {
    fun receiveRequest(): String?

    fun receiveExternalResponse() : String?
}