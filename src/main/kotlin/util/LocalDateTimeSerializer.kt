import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    override val descriptor = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        // Сериализуем LocalDateTime в строку формата ISO-8601, например, "2025-04-24T20:36:44"
        encoder.encodeString(value.format(formatter))
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        val jsonDecoder = decoder as JsonDecoder
        val jsonElement = jsonDecoder.decodeJsonElement()

        return when {
            // Обработка массива [year, month, day, hour, minute] или [year, month, day, hour, minute, second]
            jsonElement is JsonArray -> {
                val jsonArray = jsonElement.jsonArray
                require(jsonArray.size == 5 || jsonArray.size == 6) {
                    "Expected array of 5 or 6 elements for LocalDateTime, got ${jsonArray.size}"
                }

                val year = jsonArray[0].jsonPrimitive.int
                val month = jsonArray[1].jsonPrimitive.int
                val day = jsonArray[2].jsonPrimitive.int
                val hour = jsonArray[3].jsonPrimitive.int
                val minute = jsonArray[4].jsonPrimitive.int
                val second = if (jsonArray.size == 6) jsonArray[5].jsonPrimitive.int else 0

                LocalDateTime.of(year, month, day, hour, minute, second)
            }
            // Обработка строки ISO-8601, например, "2025-04-24T20:36:44"
            jsonElement is JsonPrimitive && jsonElement.isString -> {
                try {
                    LocalDateTime.parse(jsonElement.jsonPrimitive.content, formatter)
                } catch (e: Exception) {
                    throw IllegalArgumentException(
                        "Invalid ISO-8601 string format: ${jsonElement.jsonPrimitive.content}",
                        e
                    )
                }
            }

            else -> throw IllegalArgumentException("Unsupported JSON format for LocalDateTime: $jsonElement")
        }
    }
}