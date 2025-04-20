package org.healthapp.app.service

import org.healthapp.app.port.input.GetUserStatsPort
import org.healthapp.app.port.output.UserProductRepository


class GetProductsStatsService(private val userProductRepository: UserProductRepository) : GetUserStatsPort {



    override fun getUserStats(userId: Long, from: String?, to: String?){
        TODO("process statistics in this method")
    }
}

data class ProductStat(val productId: Long, val time: String, val massConsumed: Double)