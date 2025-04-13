package app.service

import org.healthapp.app.port.input.GetUserCaloriesPort
import org.healthapp.app.port.output.UserProductRepository

import org.healthapp.infrastructure.dto.Response

class GetUserCaloriesService(private val userProductRepository: UserProductRepository) : GetUserCaloriesPort {
    override fun getUserCalories(userId: Long, from: String, to: String): Response {
        val stats = userProductRepository.getCaloriesFromTo(userId, from, to)
        return Response.CaloriesResponse(1L, stats)
    }
}