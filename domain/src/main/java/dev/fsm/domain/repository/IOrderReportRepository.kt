package dev.fsm.domain.repository

import dev.fsm.domain.models.report.OrderCompleteReportParams
import dev.fsm.domain.models.report.OrderReport
import dev.fsm.domain.models.report.OrderReportParams
import dev.fsm.domain.utils.Response

interface IOrderReportRepository {
    suspend fun getNetOrderReport(params: OrderReportParams): Response<OrderReport>
    suspend fun getLocalOrderReport(params: OrderReportParams): Response<OrderReport>
    suspend fun completeOrder(params: OrderCompleteReportParams): Response<Unit>
}