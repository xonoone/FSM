package dev.fsm.domain.models.orders

data class OrdersFilterParams(
    val statusCodes: List<String> = listOf(),
    val dateFrom: String? = null,
    val dateTo: String? = null,
    val sortEarly: Boolean? = null
)