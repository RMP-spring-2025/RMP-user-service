package org.healthapp.infrastructure.adapter.input

import kotlinx.coroutines.delay

class FakeKeyDBPort : KeyDBPort {

    private val requests = mutableListOf<String>()
    private val responses = mutableListOf<String>()

    override suspend fun receiveRequest(): String? {
        delay(100)
        synchronized(requests) {
            return if (requests.isNotEmpty()) {
                requests.removeAt(0)
            } else {
                null
            }
        }
    }

    override suspend fun sendResponse(response: String) {
        delay(100)
        synchronized(responses) {
            responses.add(response)
        }
    }


    fun addRequest(request: String) {
        synchronized(requests) {
            requests.add(request)
        }
    }

    fun getResponses(): List<String> = responses.toList()
}