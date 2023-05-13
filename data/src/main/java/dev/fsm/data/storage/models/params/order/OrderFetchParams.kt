package dev.fsm.data.storage.models.params.order

import dev.fsm.domain.models.order.OrderFetchParams as DomainOrderFetchParams
data class OrderFetchParams(
    val orderId    :   String
) {
    companion object {
        fun DomainOrderFetchParams.toDomainOrderFetchParams(): OrderFetchParams = OrderFetchParams(orderId = orderId)
    }
}