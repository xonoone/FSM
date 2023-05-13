package dev.fsm.data.storage.models.params.order

import dev.fsm.domain.models.order.OrderChangeStatusParams as DomainOrderChangeStatusParams
data class OrderChangeStatusParams(
    val orderId     :   String
) {
    companion object {
        fun DomainOrderChangeStatusParams.toDomainOrderChangeStatusParams(): OrderChangeStatusParams = OrderChangeStatusParams(
            orderId = orderId
        )
    }
}