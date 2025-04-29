package org.healthapp.util

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.ClassDiscriminatorMode
import kotlinx.serialization.json.Json


object JsonSerializationConfig {
    @OptIn(ExperimentalSerializationApi::class)
    val json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
        prettyPrint = true
        classDiscriminatorMode = ClassDiscriminatorMode.NONE
        isLenient = true
    }
}