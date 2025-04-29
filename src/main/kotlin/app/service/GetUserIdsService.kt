package org.healthapp.app.service

import org.healthapp.app.domain.ProductStat
import org.healthapp.app.port.input.GetUserIdsPort
import org.healthapp.app.port.output.UserProductRepository
import java.time.LocalDateTime
import java.util.*

class GetUserIdsService(private val userProductRepository: UserProductRepository) : GetUserIdsPort {
    override fun getUserProductIds(userId: UUID, from: LocalDateTime, to: LocalDateTime): List<ProductStat> {
        return userProductRepository.getUserProductIdsFromTo(userId, from, to)
    }
}