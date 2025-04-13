package org.healthapp.infrastructure.adapter.output

import kotlinx.serialization.json.Json
import org.healthapp.infrastructure.adapter.input.interfaces.KeyDBPort
import org.healthapp.infrastructure.dto.Response

class KeyDBOutputAdapter(
    private val keyDBPort: KeyDBPort,
) {
    private val json = Json { ignoreUnknownKeys = true }
    fun sendResponse(response: Response) {
        try {
            keyDBPort.sendResponse(response)
        } catch (e: Exception) {
            println("Error ")
        }
    }
}