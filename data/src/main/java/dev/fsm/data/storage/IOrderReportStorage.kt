package dev.fsm.data.storage

import dev.fsm.data.network.api.models.order.OrderReport
import dev.fsm.data.network.api.models.order.fields.OrderField
import dev.fsm.data.storage.models.params.order.OrderCompleteReportParams
import dev.fsm.data.storage.models.params.order.OrderReportParams
import dev.fsm.data.utils.Response

sealed interface IOrderReportStorage {

    suspend fun getOrder(params: OrderReportParams) : Response<OrderReport>

    interface IRetrofit : IOrderReportStorage {
        suspend fun completeOrder(params: OrderCompleteReportParams): Response<Unit>
    }
    interface IRoom : IOrderReportStorage {
        suspend fun saveOrder(order: ArrayList<OrderField>): Response<Unit>
    }
}
