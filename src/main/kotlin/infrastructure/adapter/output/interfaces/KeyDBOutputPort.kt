package org.healthapp.infrastructure.adapter.output.interfaces

import org.healthapp.infrastructure.request.ExternalRequest
import org.healthapp.infrastructure.response.Response

interface KeyDBOutputPort {

    suspend fun sendResponse(response: Response)
    suspend fun sendRequest(request: String)
    suspend fun sendProductRequest(externalRequest: ExternalRequest): String
}