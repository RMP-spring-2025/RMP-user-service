package org.healthapp.app.port.input

interface BzuCalculationPort {
    fun calculateBzu(massConsumed: Double, bPerHundredGrams: Double, zPerHundredGrams: Double,
                     uPerHundredGrams: Double): List<Double>

}