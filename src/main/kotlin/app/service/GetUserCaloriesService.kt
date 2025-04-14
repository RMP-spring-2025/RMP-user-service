package app.service

import org.healthapp.app.port.input.GetUserCaloriesPort
import org.healthapp.app.port.output.UserProductRepository

import org.healthapp.infrastructure.dto.Response
import org.healthapp.infrastructure.dto.StatEntryDTO

class GetUserCaloriesService(private val userProductRepository: UserProductRepository) : GetUserCaloriesPort {
    override fun getUserCalories(userId: Long, from: String, to: String): List<StatEntryDTO> {
        val stats = userProductRepository.getCaloriesFromTo(userId, from, to)
        return stats
    }
}