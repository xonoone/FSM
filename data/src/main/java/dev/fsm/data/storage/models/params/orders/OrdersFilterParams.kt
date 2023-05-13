package dev.fsm.data.storage.models.params.orders

import dev.fsm.domain.models.orders.OrdersFilterParams as DomainOrdersFilterParams
class OrdersFilterParams(
    val statusCodes: List<String>,
    val dateFrom: String?,
    val dateTo: String?,
    val sortEarly: Boolean?
) {
    companion object {
        fun DomainOrdersFilterParams.toDomainOrdersFilterParams(): OrdersFilterParams = OrdersFilterParams(
            statusCodes = statusCodes,
            dateFrom = dateFrom,
            dateTo = dateTo,
            sortEarly = sortEarly
        )
    }
}