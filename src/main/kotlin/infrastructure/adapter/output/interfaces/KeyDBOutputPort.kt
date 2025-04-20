package org.healthapp.infrastructure.adapter.output.interfaces

import org.healthapp.infrastructure.dto.Response

interface KeyDBOutputPort {

    fun sendResponse(response: Response)
    fun sendRequest(request: String)
}