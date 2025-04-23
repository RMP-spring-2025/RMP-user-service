package org.healthapp.infrastructure.persistance

import java.sql.Connection
import java.sql.DriverManager

object DatabaseConfiguration {
    private var URL = System.getenv("rmp-user-service_PostgresURL")
    private var USER = System.getenv("rmp-user-service_PostgresUser")
    private var PASSWORD = System.getenv("rmp-user-service_PostgresPassword")

    fun getConnection(): Connection {
        return DriverManager.getConnection(URL, USER, PASSWORD)
    }
}