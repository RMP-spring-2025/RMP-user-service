package org.healthapp.infrastructure.handler

import kotlinx.coroutines.CompletableDeferred
import org.healthapp.infrastructure.response.ExternalResponse
import java.util.concurrent.ConcurrentHashMap

class ResponseAwaiter {
    private val awaitingResponses = ConcurrentHashMap<String, CompletableDeferred<String>>()

    // Регистрация ожидания ответа
    fun awaitResponse(correlationId: String): CompletableDeferred<String> {
        val deferred = CompletableDeferred<String>()
        awaitingResponses[correlationId] = deferred
        return deferred
    }

    // Получение ответа
    fun completeResponse(correlationId: String, response: String) {
        awaitingResponses.remove(correlationId)?.complete(response)
    }

    // Очистка при ошибке
    fun failResponse(correlationId: String?, error: Throwable) {
        awaitingResponses.remove(correlationId)?.completeExceptionally(error)
    }
}