package dev.fsm.domain.repository

import dev.fsm.domain.models.orders.Orders
import dev.fsm.domain.models.orders.OrdersFilterParams
import dev.fsm.domain.models.orders.OrdersParams
import dev.fsm.domain.utils.Response

interface IOrdersRepository {

    suspend fun getNetOrders(params: OrdersParams): Response<ArrayList<Orders>>
    suspend fun getLocalOrders(): Response<ArrayList<Orders>>

    suspend fun getNetFilteredOrders(params: OrdersFilterParams): Response<List<Orders>>
    suspend fun getLocalFilteredOrders(): Response<List<Orders>>

    suspend fun getNetArchivedOrders(params: OrdersParams): Response<List<Orders>>
    suspend fun getLocalArchivedOrders(): Response<List<Orders>>
}