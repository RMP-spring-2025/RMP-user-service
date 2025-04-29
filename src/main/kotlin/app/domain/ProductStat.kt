package org.healthapp.app.domain

import java.time.LocalDateTime


data class ProductStat(
    val productId: Long,
    val massConsumed: Double,
    val time: LocalDateTime
)