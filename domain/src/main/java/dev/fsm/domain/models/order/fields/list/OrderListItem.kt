package dev.fsm.domain.models.order.fields.list

data class OrderListItem(
    val id: String,
    val name: String,
    val isSelected: Boolean = false
)