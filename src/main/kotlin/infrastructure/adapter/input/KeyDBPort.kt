package org.healthapp.infrastructure.adapter.input

/**
 * Порт для взаимодействия с KeyDB, отвечающий за получение запросов и отправку ответов.
 */
interface KeyDBPort {

    /**
     * Получает следующий запрос из очереди запросов (например, user_request_list).
     * Возвращает null, если очередь пуста.
     */
    suspend fun receiveRequest(): String?

    /**
     * Отправляет ответ в очередь ответов (например, user_response_list).
     * @param response Строка с ответом (например, JSON).
     */
    suspend fun sendResponse(response: String)
}