package org.healthapp.app.port.input

interface CaloriesCalculationPort {
    fun calculateCalories(massConsumed: Double, caloriesPerHundredGrams: Double): Double
}