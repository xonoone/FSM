package dev.fsm.data.storage.room

import dev.fsm.data.database.dao.OrderDao
import dev.fsm.data.network.api.models.order.Order
import dev.fsm.data.storage.IOrdersStorage
import dev.fsm.data.storage.models.params.orders.OrdersFilterParams
import dev.fsm.data.storage.models.params.orders.OrdersParams
import dev.fsm.data.utils.Response
import javax.inject.Inject

class OrdersStorageRoom @Inject constructor(
    private val ordersDao: OrderDao
): IOrdersStorage.IRoom {
    override suspend fun saveOrders(orders: ArrayList<Order>): Response<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getOrders(params: OrdersParams): Response<ArrayList<Order>> {
        TODO("Not yet implemented")
    }

    override suspend fun getFilteredOrders(params: OrdersFilterParams): Response<ArrayList<Order>> {
        TODO("Not yet implemented")
    }

    override suspend fun getArchivedOrders(params: OrdersParams): Response<ArrayList<Order>> {
        TODO("Not yet implemented")
    }


}