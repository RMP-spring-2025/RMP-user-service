package org.healthapp.infrastructure.adapter.input.interfaces

import org.healthapp.infrastructure.dto.Response


interface KeyDBPort {

    fun receiveRequest(): String?

    fun sendResponse(response: Response)
}