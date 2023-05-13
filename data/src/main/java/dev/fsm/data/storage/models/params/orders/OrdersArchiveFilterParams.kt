package dev.fsm.data.storage.models.params.orders

data class OrdersArchiveFilterParams(
    val dateFrom    :   String,
    val dateTo      :   String,
    val statusCode  :   String,
    val sortEarly   :   Boolean
)