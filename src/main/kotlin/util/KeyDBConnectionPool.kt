import kotlinx.coroutines.delay
import org.healthapp.util.KeyDBConnection

class KeyDBConnectionPool(
    private val maxPoolSize: Int = 5,
    private val createConnection: () -> KeyDBConnection
) {
    private val availableConnections = mutableListOf<KeyDBConnection>()
    private val usedConnections = mutableSetOf<KeyDBConnection>()

    suspend fun <T> useConnection(block: suspend (KeyDBConnection) -> T): T {
        val connection = acquireConnection()
        return try {
            block(connection)
        } finally {
            releaseConnection(connection)
        }
    }

    private suspend fun acquireConnection(): KeyDBConnection {
        while (true) {
            synchronized(this) {
                availableConnections.firstOrNull()?.let {
                    availableConnections.remove(it)
                    usedConnections.add(it)
                    return it
                }

                if (usedConnections.size < maxPoolSize) {
                    val newConn = createConnection()
                    usedConnections.add(newConn)
                    return newConn
                }
            }
            delay(50) // Ждем перед повторной попыткой
        }
    }

    private fun releaseConnection(conn: KeyDBConnection) {
        synchronized(this) {
            usedConnections.remove(conn)
            availableConnections.add(conn)
        }
    }
}