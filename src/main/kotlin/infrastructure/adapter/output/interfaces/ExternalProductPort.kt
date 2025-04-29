package org.healthapp.infrastructure.adapter.output.interfaces

import org.healthapp.app.domain.Product

interface ExternalProductPort {
    suspend fun getProducts(productIds: List<Long>): Result<List<Product>>
}