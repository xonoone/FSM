package dev.fsm.data.storage.retrofit

import dev.fsm.data.network.api.ApiService
import dev.fsm.data.network.api.models.order.Order
import dev.fsm.data.storage.IOrdersStorage
import dev.fsm.data.storage.models.params.orders.OrdersFilterParams
import dev.fsm.data.storage.models.params.orders.OrdersParams
import dev.fsm.data.utils.Mappers.map
import dev.fsm.data.utils.Mappers.toRequestBody
import dev.fsm.data.utils.Request
import dev.fsm.data.utils.Response
import javax.inject.Inject

internal class OrdersStorageRetrofit @Inject constructor(
    private val api: ApiService,
    private val request: Request
): IOrdersStorage.IRetrofit {

    override suspend fun getOrders(params: OrdersParams): Response<ArrayList<Order>> =
        request.safeApiCallWithAuth {
            api.getOrders(
                statuses = params.statuses.toRequestBody()
            )
        }.map { it }

    override suspend fun getFilteredOrders(params: OrdersFilterParams): Response<ArrayList<Order>> =
        request.safeApiCallWithAuth {
            api.getOrders(
                dateFrom = params.dateFrom,
                dateTo = params.dateTo,
                isEarlyFirst = params.sortEarly,
                statuses = params.statusCodes.toRequestBody()
            )
        }.map { it }

    override suspend fun getArchivedOrders(params: OrdersParams): Response<ArrayList<Order>> =
        request.safeApiCallWithAuth {
            api.getOrders(
                statuses = params.statuses.toRequestBody()
            )
        }.map { it }

}