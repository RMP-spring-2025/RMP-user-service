package org.healthapp.infrastructure.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class StatEntryDTO {
    @Serializable
    data class CaloriesStat(
        @SerialName("time") val time: String,
        @SerialName("calories") val calories: Int
    ) : StatEntryDTO()
}
