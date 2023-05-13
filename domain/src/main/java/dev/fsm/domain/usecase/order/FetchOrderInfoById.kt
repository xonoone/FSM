package dev.fsm.domain.usecase.order

import dev.fsm.domain.models.order.Order
import dev.fsm.domain.models.order.OrderFetchParams
import dev.fsm.domain.repository.IOrderRepository
import dev.fsm.domain.utils.Response

class FetchOrderInfoById(
    private val repository: IOrderRepository
) {
    suspend fun get(params: OrderFetchParams): Response<Order> {
        return repository.getNetOrder(params = params)
    }
}