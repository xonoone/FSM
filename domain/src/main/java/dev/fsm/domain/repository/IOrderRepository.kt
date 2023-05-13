package dev.fsm.domain.repository

import dev.fsm.domain.models.order.Order
import dev.fsm.domain.models.order.OrderChangeStatusParams
import dev.fsm.domain.models.order.OrderFetchParams
import dev.fsm.domain.utils.Response

interface IOrderRepository {

    suspend fun getNetOrder(params: OrderFetchParams): Response<Order>
    suspend fun getLocalOrder(params: OrderFetchParams): Response<Order>
    suspend fun setStatusAccept(params: OrderChangeStatusParams): Response<Unit>
    suspend fun setStatusInRoad(params: OrderChangeStatusParams): Response<Unit>
    suspend fun setStatusActive(params: OrderChangeStatusParams): Response<Unit>
    suspend fun setStatusReject(params: OrderChangeStatusParams): Response<Unit>
}