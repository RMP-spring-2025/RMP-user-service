package org.healthapp.infrastructure.adapter.output.interfaces

import org.healthapp.infrastructure.request.ExternalRequest
import org.healthapp.infrastructure.response.Response

interface KeyDBOutputPort {

    fun sendResponse(response: Response)
    fun sendRequest(request: String)
    fun sendProductRequest(externalRequest: ExternalRequest): String
}