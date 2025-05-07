package org.healthapp.infrastructure.persistance

import java.sql.Connection
import java.sql.DriverManager

object DatabaseConfiguration {
    private var URL = System.getenv("rmp-user-service_PostgresURL") ?: "jdbc:postgresql://localhost:5432/user_db"
    private var USER = System.getenv("rmp-user-service_PostgresUser") ?:"user_postgres"
    private var PASSWORD = System.getenv("rmp-user-service_PostgresPassword") ?: "postgres"

    fun getConnection(): Connection {
        return DriverManager.getConnection(URL, USER, PASSWORD)
    }
}