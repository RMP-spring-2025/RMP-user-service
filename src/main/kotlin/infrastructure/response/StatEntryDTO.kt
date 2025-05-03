package org.healthapp.infrastructure.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class StatEntryDTO {
    @Serializable
    data class CaloriesStat(
        @SerialName("time") val time: String,
        @SerialName("calories") val calories: Int
    ) : StatEntryDTO()

    @Serializable
    data class BzuStats(
        @SerialName("time") val time: String,
        @SerialName("B") val B: Int,
        @SerialName("Z") val Z: Int,
        @SerialName("U") val U: Int
    ) : StatEntryDTO()


    @Serializable
    data class ProductStat(
        @SerialName("time") val time: String,
        @SerialName("name") val name: String,
        @SerialName("calories") val calories: Int
    ) : StatEntryDTO()

    @Serializable
    data class WeightStat(
        @SerialName("weight") val weight: Double,
        @SerialName("time") val time: String
    ) : StatEntryDTO()
}
