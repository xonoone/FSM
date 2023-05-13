package dev.fsm.data.storage.models.params.orders

import dev.fsm.domain.models.orders.OrdersParams as DomainOrdersParams

data class OrdersParams(
    val statuses   :   ArrayList<String>
) {
    companion object {
        fun DomainOrdersParams.toDomainOrdersParams(): OrdersParams = OrdersParams(statuses = statuses)
    }
}