package dev.fsm.data.storage

import dev.fsm.data.network.api.models.order.Order
import dev.fsm.data.storage.models.params.orders.OrdersFilterParams
import dev.fsm.data.storage.models.params.orders.OrdersParams
import dev.fsm.data.utils.Response
sealed interface IOrdersStorage {
    suspend fun getOrders(params: OrdersParams): Response<ArrayList<Order>>
    suspend fun getFilteredOrders(params: OrdersFilterParams): Response<ArrayList<Order>>
    suspend fun getArchivedOrders(params: OrdersParams): Response<ArrayList<Order>>
    interface IRetrofit : IOrdersStorage
    interface IRoom : IOrdersStorage {
        suspend fun saveOrders(orders: ArrayList<Order>): Response<Unit>
    }
}