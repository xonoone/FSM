package dev.fsm.data.repository

import dev.fsm.data.network.api.models.order.OrderReport.Companion.toOrderReport
import dev.fsm.data.storage.models.params.order.OrderCompleteReportParams.Companion.toOrderCompleteReportParams
import dev.fsm.data.storage.models.params.order.OrderReportParams.Companion.toOrderReportParams
import dev.fsm.data.storage.retrofit.OrderReportStorageRetrofit
import dev.fsm.data.storage.room.OrderReportStorageRoom
import dev.fsm.data.utils.Mappers.toResponse
import dev.fsm.domain.models.report.OrderCompleteReportParams
import dev.fsm.domain.models.report.OrderReport
import dev.fsm.domain.models.report.OrderReportParams
import dev.fsm.domain.repository.IOrderReportRepository
import dev.fsm.domain.utils.Response

internal class OrderReportRepositoryImpl(
    private val network: OrderReportStorageRetrofit,
    private val database: OrderReportStorageRoom
) : IOrderReportRepository {
    override suspend fun getNetOrderReport(params: OrderReportParams): Response<OrderReport> {
        val response = network.getOrder(params = params.toOrderReportParams())
        return response.toResponse { it.toOrderReport() }
    }

    override suspend fun getLocalOrderReport(params: OrderReportParams): Response<OrderReport> {
        val response = database.getOrder(params = params.toOrderReportParams())
        return response.toResponse { it.toOrderReport() }
    }

    override suspend fun completeOrder(params: OrderCompleteReportParams): Response<Unit> {
        val response = network.completeOrder(params = params.toOrderCompleteReportParams())
        return response.toResponse {}
    }
}