package dev.fsm.domain.models.orders

data class OrdersArchiveFilterParams(
    val dateFrom    :   String,
    val dateTo      :   String,
    val statusCode  :   String,
    val sortEarly   :   Boolean
)