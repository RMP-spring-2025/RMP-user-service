package org.healthapp.infrastructure.persistance

import liquibase.Contexts
import liquibase.LabelExpression
import liquibase.Liquibase
import liquibase.database.Database
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import java.util.logging.Logger

class LiquibaseRunner(
    private val changelogFile: String
) {
    private val logger = Logger.getLogger(LiquibaseRunner::class.java.name)

    fun runMigrations() {
        try {
            val database: Database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(JdbcConnection(DatabaseConfiguration.getConnection()))
            val liquibase = Liquibase(changelogFile, ClassLoaderResourceAccessor(),database )
            liquibase.update(Contexts(), LabelExpression())
            logger.info("Liquibase migrations applied successfully")
        } catch (e: Exception) {
            logger.severe("Failed to apply Liquibase migrations: ${e.message}")
            throw RuntimeException("Liquibase migration failed", e)
        }
    }
}