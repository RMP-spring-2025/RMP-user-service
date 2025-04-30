package org.healthapp.app.service

import org.healthapp.app.domain.BzuStat
import org.healthapp.app.domain.CaloriesStat
import org.healthapp.app.domain.Product
import org.healthapp.app.domain.ProductStat
import org.healthapp.app.port.input.BzuCalculationPort
import org.healthapp.app.port.input.GetUserBzuPort

class GetUserBzuService(val bzuCalculationPort: BzuCalculationPort): GetUserBzuPort {
    override fun calculateBzu(
        productStats: List<ProductStat>,
        response: List<Product>):
            List<BzuStat> {
        val productMap = response.associateBy { it.id }

        return productStats.mapNotNull { stat ->
            val product = productMap[stat.productId] ?: return@mapNotNull null
            val bzu: List<Double> = bzuCalculationPort.calculateBzu(stat.massConsumed, product.bPerHundredGrams, product.zPerHundredGrams, product.uPerHundredGrams)
            if (bzu.isEmpty()) null
            else BzuStat(time = stat.time, B = bzu[0].toInt(), Z = bzu[1].toInt(), U = bzu[2].toInt())
        }
    }
}