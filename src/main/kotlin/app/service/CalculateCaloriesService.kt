package org.healthapp.app.service

import org.healthapp.app.port.input.CaloriesCalculationPort

class CalculateCaloriesService : CaloriesCalculationPort {
    override fun calculateCalories(massConsumed: Double, caloriesPerHundredGrams: Double): Double {
        if (massConsumed < 0.0 || caloriesPerHundredGrams <= 0.0)
            return 0.0

        return (massConsumed * caloriesPerHundredGrams / 100)
    }
}