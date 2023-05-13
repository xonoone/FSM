package dev.fsm.data.repository

import dev.fsm.data.network.api.models.order.Order.Companion.toOrders
import dev.fsm.data.storage.models.params.orders.OrdersFilterParams.Companion.toDomainOrdersFilterParams
import dev.fsm.data.storage.models.params.orders.OrdersParams.Companion.toDomainOrdersParams
import dev.fsm.data.storage.retrofit.OrdersStorageRetrofit
import dev.fsm.data.storage.room.OrdersStorageRoom
import dev.fsm.data.utils.Mappers.toResponse
import dev.fsm.domain.models.orders.Orders
import dev.fsm.domain.models.orders.OrdersFilterParams
import dev.fsm.domain.models.orders.OrdersParams
import dev.fsm.domain.repository.IOrdersRepository
import dev.fsm.domain.utils.Response
import javax.inject.Inject

internal class OrdersRepositoryImpl @Inject constructor(
    private val network: OrdersStorageRetrofit,
    private val database: OrdersStorageRoom
) : IOrdersRepository {
    override suspend fun getNetOrders(params: OrdersParams): Response<ArrayList<Orders>> {
        val response = network.getOrders(params = params.toDomainOrdersParams())
        return response.toResponse { orders -> orders.map { order -> order.toOrders() } as ArrayList<Orders> }
    }

    override suspend fun getLocalOrders(): Response<ArrayList<Orders>> {
        TODO()
    }

    override suspend fun getNetFilteredOrders(
        params: OrdersFilterParams
    ): Response<ArrayList<Orders>> {
        val response = network.getFilteredOrders(params = params.toDomainOrdersFilterParams())
        return response.toResponse { orders ->
            orders.map { order -> order.toOrders() } as ArrayList<Orders>
        }
    }

    override suspend fun getLocalFilteredOrders(): Response<ArrayList<Orders>> {
        TODO()
    }

    override suspend fun getNetArchivedOrders(params: OrdersParams): Response<ArrayList<Orders>> {
        val response = network.getOrders(params = params.toDomainOrdersParams())
        return response.toResponse { orders ->
            orders.map { order -> order.toOrders() } as ArrayList<Orders>
        }
    }

    override suspend fun getLocalArchivedOrders(): Response<ArrayList<Orders>> {
        TODO()
    }
}