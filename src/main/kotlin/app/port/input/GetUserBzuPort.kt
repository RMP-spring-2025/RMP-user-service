package org.healthapp.app.port.input

import org.healthapp.app.domain.BzuStat
import org.healthapp.app.domain.Product
import org.healthapp.app.domain.ProductStat

interface GetUserBzuPort {

    fun calculateBzu(productStats: List<ProductStat>, response: List<Product>): List<BzuStat>
}