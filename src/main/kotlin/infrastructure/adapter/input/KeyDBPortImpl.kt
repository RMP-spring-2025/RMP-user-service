package org.healthapp.infrastructure.adapter.input

import io.lettuce.core.RedisClient
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.sync.RedisCommands
import org.healthapp.infrastructure.adapter.input.interfaces.KeyDBPort
import org.healthapp.infrastructure.dto.Response
import org.healthapp.util.JsonSerializationConfig

class KeyDBPortImpl(
    private val host: String = "localhost",
    private val port: Int = 6379,
    private val requestQueue: String = "user_request_list",
    private val responseQueue: String = "user_response_list"
) : KeyDBPort {
    private val redisClient: RedisClient = RedisClient.create("redis://$host:$port")
    private val connection: StatefulRedisConnection<String, String> = redisClient.connect()
    private val commands: RedisCommands<String, String> = connection.sync()

    override fun receiveRequest(): String? {
        return commands.brpop(1, requestQueue)?.value
    }

    override fun sendResponse(response: Response) {
        val jsonString = JsonSerializationConfig.json.encodeToString(response)
        println(jsonString)
        commands.lpush(responseQueue, jsonString)
    }

    fun sendRequest(request: String) {
        commands.lpush(requestQueue, request)
    }

    fun shutdown() {
        connection.close()
        redisClient.shutdown()
    }
}