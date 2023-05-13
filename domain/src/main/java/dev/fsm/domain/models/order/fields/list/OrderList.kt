package dev.fsm.domain.models.order.fields.list

data class OrderList(
    val id             : String,
    val name           : String,
    val isSingle       : Boolean             = false,
    val values         : List<OrderListItem> = listOf(),
)