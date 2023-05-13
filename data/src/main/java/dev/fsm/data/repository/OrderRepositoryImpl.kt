package dev.fsm.data.repository

import dev.fsm.data.network.api.models.order.Order.Companion.toOrder
import dev.fsm.data.storage.models.params.order.OrderChangeStatusParams.Companion.toDomainOrderChangeStatusParams
import dev.fsm.data.storage.models.params.order.OrderFetchParams.Companion.toDomainOrderFetchParams
import dev.fsm.data.storage.retrofit.OrderStorageRetrofit
import dev.fsm.data.storage.room.OrderStorageRoom
import dev.fsm.data.utils.Mappers.toResponse
import dev.fsm.domain.models.order.Order
import dev.fsm.domain.models.order.OrderChangeStatusParams
import dev.fsm.domain.models.order.OrderFetchParams
import dev.fsm.domain.repository.IOrderRepository
import dev.fsm.domain.utils.Response
import javax.inject.Inject

internal class OrderRepositoryImpl @Inject constructor(
    private val network: OrderStorageRetrofit,
    private val database: OrderStorageRoom
) : IOrderRepository {

    override suspend fun getNetOrder(params: OrderFetchParams): Response<Order> {
        val response = network.getOrder(params = params.toDomainOrderFetchParams())
        return response.toResponse { it.toOrder() }
    }

    override suspend fun getLocalOrder(params: OrderFetchParams): Response<Order> { TODO() }
    override suspend fun setStatusAccept(params: OrderChangeStatusParams): Response<Unit> {
        val response = network.setStatusAccept(params = params.toDomainOrderChangeStatusParams())
        return response.toResponse {}
    }

    override suspend fun setStatusInRoad(params: OrderChangeStatusParams): Response<Unit> {
        val response = network.setStatusInRoad(params = params.toDomainOrderChangeStatusParams())
        return response.toResponse {}
    }

    override suspend fun setStatusActive(params: OrderChangeStatusParams): Response<Unit> {
        val response = network.setStatusActive(params = params.toDomainOrderChangeStatusParams())
        return response.toResponse {}
    }

    override suspend fun setStatusReject(params: OrderChangeStatusParams): Response<Unit> {
        val response = network.setStatusReject(params = params.toDomainOrderChangeStatusParams())
        return response.toResponse {}
    }
}