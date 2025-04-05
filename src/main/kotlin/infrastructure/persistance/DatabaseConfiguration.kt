package org.healthapp.infrastructure.persistance

import java.sql.Connection
import java.sql.DriverManager

object DatabaseConfiguration {
        private const val URL = "jdbc:postgresql://localhost:5432/your_database"
        private const val USER = "user_name"
        private const val PASSWORD = "your_password"

        fun getConnection(): Connection {
            return DriverManager.getConnection(URL, USER, PASSWORD)
        }
}