package org.healthapp.app.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
enum class UserGoal(val goalName: String){
    @SerialName("gain weight") GAIN_WEIGHT("gain weight"),
    @SerialName("loose weight") LOOSE_WEIGHT("loose weight"),
    @SerialName("keep weight") KEEP_WEIGHT("keep weight")
}

@Serializable
enum class Sex(val sex: String){
    @SerialName("F") FEMALE("Female"),
    @SerialName("M") MALE("Male")
 }
data class User(val id: UUID, val username: String, val age: Int, val height: Double, val userGoal: UserGoal, val sex: Sex)