package app.service

import org.healthapp.app.domain.ProductConsumption
import org.healthapp.app.domain.User
import org.healthapp.app.port.input.AddProductConsumptionPort
import org.healthapp.app.port.output.UserProductRepository
import org.healthapp.infrastructure.dto.ProductConsumptionDTO

class AddProductConsumptionService(private val userProductRepository: UserProductRepository) :
    AddProductConsumptionPort {
    override fun addUserConsumedProduct(consumedProduct: ProductConsumptionDTO) : Boolean {
//        find user by user ID in DB
        val user = User(consumedProduct.userId, "User_${consumedProduct.userId}")
        val consumption = ProductConsumption(
            user = user,
            productId = consumedProduct.productId,
            mass = consumedProduct.massConsumed,
            timeStamp = consumedProduct.timestamp
        )
        return userProductRepository.saveUserConsumedProduct(consumption)
    }
}