package dev.fsm.data.storage.retrofit

import dev.fsm.data.network.api.ApiService
import dev.fsm.data.network.api.models.order.Order.Companion.toOrderReport
import dev.fsm.data.network.api.models.order.OrderReport
import dev.fsm.data.storage.IOrderReportStorage
import dev.fsm.data.storage.models.params.order.OrderCompleteReportParams
import dev.fsm.data.storage.models.params.order.OrderReportParams
import dev.fsm.data.utils.Converter.parsFields
import dev.fsm.data.utils.Mappers.map
import dev.fsm.data.utils.Mappers.toRequestBody
import dev.fsm.data.utils.Request
import dev.fsm.data.utils.Response
import javax.inject.Inject

internal class OrderReportStorageRetrofit @Inject constructor(
    private val api: ApiService,
    private val request: Request
) : IOrderReportStorage.IRetrofit {
    override suspend fun completeOrder(params: OrderCompleteReportParams): Response<Unit> =
        request.safeApiCallWithAuth {
            api.completeOrder(
                id = params.orderId,
                fieldsJson = params.reportFields.toRequestBody()
            )
        }.map {}

    override suspend fun getOrder(params: OrderReportParams): Response<OrderReport> =
        request.safeApiCallWithAuth {
            api.getOrder(id = params.orderId)
        }.map { order ->
            order.template?.orderFields?.parsFields()
            order.template?.reportFields?.parsFields()
            order.toOrderReport()
        }
}