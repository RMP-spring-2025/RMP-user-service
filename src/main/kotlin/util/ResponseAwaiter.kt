import kotlinx.coroutines.CompletableDeferred
import java.util.concurrent.ConcurrentHashMap

class ResponseAwaiter {
    private val awaitingResponses = ConcurrentHashMap<String, CompletableDeferred<String>>()
    private val pendingResponses = ConcurrentHashMap<String, String>()

    fun awaitResponse(correlationId: String): CompletableDeferred<String> {
        val deferred = CompletableDeferred<String>()
        // Проверяем, есть ли уже ответ для correlationId
        pendingResponses.remove(correlationId)?.let { response ->
            println("Found pending response for correlationId: $correlationId")
            deferred.complete(response)
            return deferred
        }
        awaitingResponses[correlationId] = deferred
        println("Registered awaiting response for correlationId: $correlationId")
        return deferred
    }

    fun completeResponse(correlationId: String, response: String) {
        println("Completing response for correlationId: $correlationId with response: $response")
        // Проверяем, есть ли зарегистрированный CompletableDeferred
        awaitingResponses.remove(correlationId)?.complete(response)
            ?: run {
                // Если нет, сохраняем ответ в pendingResponses
                println("No awaiting response for correlationId: $correlationId, storing as pending")
                pendingResponses[correlationId] = response
            }
    }

    fun failResponse(correlationId: String?, error: Throwable) {
        println("Failing response for correlationId: $correlationId with error: ${error.message}")
        if (correlationId != null) {
            awaitingResponses.remove(correlationId)?.completeExceptionally(error)
            pendingResponses.remove(correlationId) // Очищаем, если был сохранен
        }
    }
}