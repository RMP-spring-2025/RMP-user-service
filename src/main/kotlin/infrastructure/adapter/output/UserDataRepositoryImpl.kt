package org.healthapp.infrastructure.adapter.output

import org.healthapp.app.domain.User
import org.healthapp.app.domain.UserWeight
import org.healthapp.app.port.output.UserDataRepository
import org.healthapp.infrastructure.persistance.DatabaseConfiguration
import org.healthapp.infrastructure.persistance.Queries
import java.time.LocalDateTime
import java.util.*

class UserDataRepositoryImpl : UserDataRepository {
    override fun createUser(user: User): Boolean {
        return try {
            DatabaseConfiguration.getConnection().use { connection ->
                val statement = connection.prepareStatement(Queries.ADD_USER.query.trimIndent())
                statement.setObject(1, user.id)
                statement.setString(2, user.username)
                statement.setInt(3, user.age)
                statement.setDouble(4, user.height)
                statement.executeUpdate() > 0
            }
        } catch (e: Exception) {
            false
        }
    }

    override fun addWeight(userWeight: UserWeight): Boolean {
        return try {
            DatabaseConfiguration.getConnection().use { connection ->
                val statement = connection.prepareStatement(Queries.ADD_USER_WEIGHT.query.trimIndent())
                statement.setObject(1, userWeight.userId)
                statement.setDouble(2, userWeight.weight)
                statement.setObject(3, userWeight.time)
                statement.executeUpdate() > 0
            }
        } catch (e: Exception) {
            false
        }
    }

    override fun getUserWeightFromTo(
        userId: UUID,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<UserWeight> {
        return try {
            DatabaseConfiguration.getConnection().use { connection ->
                val statement = connection.prepareStatement(Queries.GET_USER_WEIGHT_FROM_TO.query.trimIndent())
                statement.setObject(1, userId)
                statement.setObject(2, from)
                statement.setObject(3, to)
                statement.executeQuery().use { rs ->
                    val userWeights = mutableListOf<UserWeight>()
                    while (rs.next()) {
                        userWeights.add(
                            UserWeight(
                                userId = rs.getObject("user_id", UUID::class.java),
                                weight = rs.getDouble("weight"),
                                time = rs.getObject("timestamp", LocalDateTime::class.java)
                            )
                        )
                    }
                    userWeights
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}