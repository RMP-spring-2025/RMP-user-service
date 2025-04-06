package org.healthapp.infrastructure.persistance

import java.sql.Connection
import java.sql.DriverManager

object DatabaseConfiguration {
        private const val URL = "jdbc:postgresql://localhost:5432/user_db"
        private const val USER = "user_postgres"
        private const val PASSWORD = "postgres"

        fun getConnection(): Connection {
            return DriverManager.getConnection(URL, USER, PASSWORD)
        }
}