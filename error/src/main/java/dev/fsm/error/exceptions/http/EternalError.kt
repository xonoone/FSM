package dev.fsm.error.exceptions.http

import dev.fsm.error.exceptions.ServerException
import dev.fsm.error.utils.Codes.Http.ETERNAL
import dev.fsm.error.utils.InRange

open class EternalError(
    @InRange(ETERNAL.start, ETERNAL.end) httpCode: Int? = ETERNAL.start,
    apiCode: Int? = -1,
) : ServerException(
    httpCode = httpCode,
    apiCode = apiCode
) {
    init {
        val fields = this::class.java.declaredFields
        fields.forEach { field ->
            field.annotations.forEach { _ ->
                if (field.isAnnotationPresent(InRange::class.java)) {
                    val range = field.getAnnotation(InRange::class.java)
                    if (httpCode != null && httpCode !in range.start..range.end)
                        throw IllegalArgumentException(
                            "HttpCode out of range: " +
                                    "HttpCode = $httpCode / range = ${range.start..range.end}"
                        )
                }
            }
        }
    }

    override val message: String
        get() = "Server error. Try later."
}