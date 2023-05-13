package dev.fsm.data.storage.retrofit

import dev.fsm.data.network.api.ApiService
import dev.fsm.data.network.api.models.order.Order
import dev.fsm.data.storage.IOrderStorage
import dev.fsm.data.storage.models.params.order.OrderChangeStatusParams
import dev.fsm.data.storage.models.params.order.OrderFetchParams
import dev.fsm.data.utils.Converter.parsFields
import dev.fsm.data.utils.Mappers.map
import dev.fsm.data.utils.Request
import dev.fsm.data.utils.Response
import javax.inject.Inject

internal class OrderStorageRetrofit @Inject constructor(
    private val api: ApiService,
    private val request: Request,
): IOrderStorage.IRetrofit {

    override suspend fun getOrder(params: OrderFetchParams): Response<Order> =
        request.safeApiCallWithAuth {
            api.getOrder(
                id = params.orderId
            )
        }.map { order ->
            order.template?.orderFields?.parsFields()
            order.template?.reportFields?.parsFields()
            order
        }

    override suspend fun setStatusAccept(params: OrderChangeStatusParams): Response<Unit> =
        request.safeApiCallWithAuth {
            api.setStatusAccept(orderId = params.orderId)
        }.map {}

    override suspend fun setStatusInRoad(params: OrderChangeStatusParams): Response<Unit> =
        request.safeApiCallWithAuth {
            api.setStatusInRoad(orderId = params.orderId)
        }.map {}
    override suspend fun setStatusActive(params: OrderChangeStatusParams): Response<Unit> =
        request.safeApiCallWithAuth {
            api.setStatusActive(orderId = params.orderId)
        }.map {}

    override suspend fun setStatusReject(params: OrderChangeStatusParams): Response<Unit> =
        request.safeApiCallWithAuth {
            api.setStatusReject(orderId = params.orderId)
        }.map {}
}