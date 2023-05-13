package dev.fsm.presentation.screens.orders.models

import dev.fsm.domain.models.order.Status
import dev.fsm.domain.models.orders.Orders as DomainOrders
import java.time.LocalDateTime

data class Orders(
    val id: String,
    val name: String,
    val type: String,
    val status: Status,
    val address: String? = null,
    var isChecked: Boolean = false,
    val date: LocalDateTime?,
    val duration: LocalDateTime?
) {
    companion object {
        fun DomainOrders.toOrders(): Orders = Orders(
            id = id,
            name = name,
            type = type,
            status = status,
            address = address,
            isChecked = isChecked,
            date = date,
            duration = duration
        )
    }
}