package dev.fsm.data.storage

import dev.fsm.data.database.entities.Order
import dev.fsm.data.network.api.models.order.Order as NetworkOrder
import dev.fsm.data.storage.models.params.order.OrderChangeStatusParams
import dev.fsm.data.storage.models.params.order.OrderFetchParams
import dev.fsm.data.utils.Response

sealed interface IOrderStorage {

    interface IRetrofit : IOrderStorage {
        suspend fun getOrder(params: OrderFetchParams): Response<NetworkOrder>
        suspend fun setStatusAccept(params: OrderChangeStatusParams): Response<Unit>
        suspend fun setStatusInRoad(params: OrderChangeStatusParams): Response<Unit>
        suspend fun setStatusActive(params: OrderChangeStatusParams): Response<Unit>
        suspend fun setStatusReject(params: OrderChangeStatusParams): Response<Unit>
    }
    interface IRoom : IOrderStorage {
        suspend fun get(): Response<Order>
        suspend fun save(order: Order): Response<Unit>
        suspend fun clear(): Response<Unit>
    }
}