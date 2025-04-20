package org.healthapp.util

import io.lettuce.core.RedisClient
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.sync.RedisCommands

class KeyDBConnection(
    host: String = "localhost",
    port: Int = 6379,
) {

    private val redisClient: RedisClient = RedisClient.create("redis://$host:$port")
    private val connection: StatefulRedisConnection<String, String> = redisClient.connect()
    val commands: RedisCommands<String, String> = connection.sync()

    fun shutDown() {
        connection.close()
    }
}