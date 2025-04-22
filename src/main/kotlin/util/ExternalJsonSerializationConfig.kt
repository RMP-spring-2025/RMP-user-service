package org.healthapp.util

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

class ExternalJsonSerializationConfig {
    @OptIn(ExperimentalSerializationApi::class)
    val json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
        prettyPrint = true
        isLenient = true
    }
}