package dev.fsm.domain.models.orders

import dev.fsm.domain.models.order.Status
import dev.fsm.domain.utils.IServerDateConverter.ServerDateConverter.toLocalDateTime
import java.time.LocalDateTime

data class Orders(
    val id: String,
    val name: String,
    val type: String,
    val status: Status,
    val address: String? = null,
    val isChecked: Boolean = false,
    val date: LocalDateTime?,
    val duration: LocalDateTime?
) {
    constructor(
        id: String,
        name: String,
        type: String,
        status: Status,
        address: String? = null,
        isChecked: Boolean = false,
        date: String,
        duration: String
    ) : this(
        id = id,
        name = name,
        type = type,
        status = status,
        address = address,
        isChecked = isChecked,
        date = try {
            toLocalDateTime(date)
        } catch (_: Exception) {
            null
        },
        duration = try {
            toLocalDateTime(duration)
        } catch (_: Exception) {
            null
        }
    )
}