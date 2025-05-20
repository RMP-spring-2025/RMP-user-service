package org.healthapp.util

import io.lettuce.core.RedisClient
import io.lettuce.core.RedisURI
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.sync.RedisCommands
import io.lettuce.core.support.ConnectionPoolSupport
import org.apache.commons.pool2.impl.GenericObjectPool
import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class KeyDBConnection {

    private val redisClient: RedisClient
    private val connectionPool: GenericObjectPool<StatefulRedisConnection<String, String>>

    init {

        val redisUrl = System.getenv("rmp-user-service_KeyDBURL") ?: "redis://localhost:6379"
        val redisUri = RedisURI.create(redisUrl)

        redisClient = RedisClient.create(redisUri)

        val poolConfig = GenericObjectPoolConfig<StatefulRedisConnection<String, String>>().apply {
            maxTotal = 20
            maxIdle = 10
            minIdle = 5
            testOnBorrow = true
            testOnReturn = true
        }

        connectionPool = ConnectionPoolSupport.createGenericObjectPool(
            { redisClient.connect() },
            poolConfig
        )
    }

    suspend fun <T> withConnection(action: (RedisCommands<String, String>) -> T): T = withContext(Dispatchers.IO) {
        val connection = connectionPool.borrowObject()
        try {
            val commands = connection.sync()
            action(commands)
        } finally {
            connectionPool.returnObject(connection)
        }
    }

    fun shutDown() {
        connectionPool.close()
        redisClient.shutdown()
    }
}