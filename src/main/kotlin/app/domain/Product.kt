package org.healthapp.app.domain


data class Product(val id: Long, val name: String, val bPerHundredGrams: Double, val zPerHundredGrams: Double, val uPerHundredGrams: Double, val caloriesPerHundredGrams: Double)
