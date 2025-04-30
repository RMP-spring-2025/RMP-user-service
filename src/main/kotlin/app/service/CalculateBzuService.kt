package org.healthapp.app.service

import org.healthapp.app.port.input.BzuCalculationPort

class CalculateBzuService: BzuCalculationPort {
    override fun calculateBzu(
        massConsumed: Double,
        bPerHundredGrams: Double,
        zPerHundredGrams: Double,
        uPerHundredGrams: Double
    ): List<Double> {
        if (massConsumed < 0.0 || bPerHundredGrams <= 0.0 || zPerHundredGrams <= 0.0 || uPerHundredGrams <= 0.0)
            return emptyList()
        return listOf(massConsumed * bPerHundredGrams / 100,
            massConsumed * zPerHundredGrams / 100, massConsumed * uPerHundredGrams / 100)
    }
}