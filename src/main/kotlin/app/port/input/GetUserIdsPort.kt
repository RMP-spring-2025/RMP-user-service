package org.healthapp.app.port.input

import org.healthapp.app.domain.ProductStat
import java.time.LocalDateTime
import java.util.*

interface GetUserIdsPort {
    fun getUserProductIds(userId: UUID, from: LocalDateTime, to: LocalDateTime): List<ProductStat>
}