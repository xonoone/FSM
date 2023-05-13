package dev.fsm.domain.usecase.order

import dev.fsm.domain.models.order.OrderChangeStatusParams
import dev.fsm.domain.repository.IOrderRepository
import dev.fsm.domain.utils.OrderStatuses
import dev.fsm.domain.utils.Response

class ChangeOrderStatusById(
    private val repository: IOrderRepository
) {
    suspend fun changeStatus(params: OrderChangeStatusParams): Response<Unit>  {
        return when (params.statusCode) {
            OrderStatuses.ACCEPTED -> repository.setStatusAccept(params = params)
            OrderStatuses.INROAD -> repository.setStatusInRoad(params = params)
            OrderStatuses.ACTIVE -> repository.setStatusActive(params = params)
            OrderStatuses.REJECTED -> repository.setStatusReject(params = params)
            else -> Response.Error(exception = NoSuchElementException("Not found status: ${params.statusCode}"))
        }
    }
}