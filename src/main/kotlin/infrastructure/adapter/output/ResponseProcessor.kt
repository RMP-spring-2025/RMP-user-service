package org.healthapp.infrastructure.adapter.output


import org.healthapp.infrastructure.adapter.output.interfaces.KeyDBOutputPort
import org.healthapp.infrastructure.response.Response

class ResponseProcessor(
    private val keyDBPort: KeyDBOutputPort,
) {
    fun sendResponse(response: Response) {
        try {
            keyDBPort.sendResponse(response)
        } catch (e: Exception) {
            println("Error")
        }
    }
}