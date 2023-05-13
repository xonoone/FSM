package dev.fsm.domain.usecase.orders

import dev.fsm.domain.models.orders.Orders
import dev.fsm.domain.models.orders.OrdersFilterParams
import dev.fsm.domain.models.orders.OrdersParams
import dev.fsm.domain.repository.IOrdersRepository
import dev.fsm.domain.utils.OrderStatuses.ACCEPTED
import dev.fsm.domain.utils.OrderStatuses.ACTIVE
import dev.fsm.domain.utils.OrderStatuses.INROAD
import dev.fsm.domain.utils.Response

class FetchActualOrdersByPage(
    private val repository: IOrdersRepository
) {
    private val statuses = arrayListOf(ACCEPTED, INROAD, ACTIVE)
    suspend fun get(): Response<ArrayList<Orders>> {
        val params = OrdersParams(
            statuses = statuses
        )
        return repository.getNetOrders(params = params)
    }

    suspend fun getFiltered(params: OrdersFilterParams): Response<List<Orders>> {
        val filterParams = OrdersFilterParams(
            statusCodes = statuses,
            dateFrom = params.dateFrom,
            dateTo = params.dateTo,
            sortEarly = params.sortEarly
        )
        return repository.getNetFilteredOrders(params = filterParams)
    }
}