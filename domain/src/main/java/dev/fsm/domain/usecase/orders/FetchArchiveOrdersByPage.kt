package dev.fsm.domain.usecase.orders

import dev.fsm.domain.models.orders.Orders
import dev.fsm.domain.models.orders.OrdersFilterParams
import dev.fsm.domain.models.orders.OrdersParams
import dev.fsm.domain.repository.IOrdersRepository
import dev.fsm.domain.utils.OrderStatuses.CANCELED
import dev.fsm.domain.utils.OrderStatuses.COMPLETED
import dev.fsm.domain.utils.OrderStatuses.REJECTED
import dev.fsm.domain.utils.Response

class FetchArchiveOrdersByPage(
    private val repository: IOrdersRepository
) {

    suspend fun get(): Response<List<Orders>> {
        val statuses = arrayListOf(REJECTED, CANCELED, COMPLETED)
        val params = OrdersParams(statuses = statuses)
        return repository.getNetArchivedOrders(params = params)
    }

    suspend fun getFiltered(params: OrdersFilterParams): Response<List<Orders>> {
        return repository.getNetFilteredOrders(params = params)
    }
}