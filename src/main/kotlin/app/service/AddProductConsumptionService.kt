package app.service

import org.healthapp.app.domain.ProductConsumption
import org.healthapp.app.domain.User
import org.healthapp.app.port.input.AddProductConsumptionPort
import org.healthapp.app.port.output.UserProductRepository
import org.healthapp.infrastructure.dto.ProductConsumptionDto

class AddProductConsumptionService(private val userProductRepository: UserProductRepository): AddProductConsumptionPort {
    override fun addUserConsumedProduct(consumedProduct: ProductConsumptionDto) {
        val user = User(consumedProduct.userId, "User_${consumedProduct.userId}", "password")
        val consumption = ProductConsumption(
            id = System.currentTimeMillis(),
            user = user,
            productId = consumedProduct.productId,
            mass = consumedProduct.massConsumed,
            timeStamp = consumedProduct.timestamp
        )
        userProductRepository.saveUserConsumedProduct(consumption)
    }
}