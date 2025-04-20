package app.service

import org.healthapp.app.port.input.GetUserCaloriesPort
import org.healthapp.app.port.output.UserProductRepository
import org.healthapp.infrastructure.dto.StatEntryDTO
import java.util.UUID

class GetUserCaloriesService(private val userProductRepository: UserProductRepository) : GetUserCaloriesPort {
    override fun getUserCalories(userId: UUID, from: String, to: String): List<StatEntryDTO> {
        val stats = userProductRepository.getCaloriesFromTo(userId, from, to)
        return stats
    }
}