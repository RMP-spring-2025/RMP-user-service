package org.healthapp.infrastructure.adapter.output

import org.healthapp.app.domain.ProductConsumption
import org.healthapp.app.port.output.UserProductRepository

class UserProductInMemoryRepositoryImpl: UserProductRepository {
    private val consumedProducts = mutableListOf<ProductConsumption>()
    override fun saveUserConsumedProduct(consumedProduct: ProductConsumption) {
        consumedProducts.add(consumedProduct)
        println(consumedProduct)
    }
}