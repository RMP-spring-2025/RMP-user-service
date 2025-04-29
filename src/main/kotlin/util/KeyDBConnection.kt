package org.healthapp.util

import io.lettuce.core.RedisClient
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.sync.RedisCommands

class KeyDBConnection() {

    private val redisClient: RedisClient = RedisClient.create(System.getenv("rmp-user-service_KeyDBURL"))
    private val connection: StatefulRedisConnection<String, String> = redisClient.connect()
    val commands: RedisCommands<String, String> = connection.sync()

    fun shutDown() {
        connection.close()
    }
}