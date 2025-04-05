package org.healthapp.app.domain

data class ProductConsumption(val id: Long, val user: User, val productId: Long, val mass: Double, val timeStamp: String)