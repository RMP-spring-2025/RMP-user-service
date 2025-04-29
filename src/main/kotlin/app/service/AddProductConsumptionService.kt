package app.service

import org.healthapp.app.domain.ProductConsumption
import org.healthapp.app.port.input.AddProductConsumptionPort
import org.healthapp.app.port.output.UserProductRepository

class AddProductConsumptionService(private val userProductRepository: UserProductRepository) :
    AddProductConsumptionPort {
    override fun addUserConsumedProduct(consumedProduct: ProductConsumption): Boolean {
//        find user by user ID in DB

        return userProductRepository.saveUserConsumedProduct(consumedProduct)
    }
}