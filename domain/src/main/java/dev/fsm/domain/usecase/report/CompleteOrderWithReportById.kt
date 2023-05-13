package dev.fsm.domain.usecase.report

import dev.fsm.domain.models.report.OrderCompleteReportParams
import dev.fsm.domain.models.report.OrderReport
import dev.fsm.domain.models.report.OrderReportParams
import dev.fsm.domain.repository.IOrderReportRepository
import dev.fsm.domain.utils.Response

class CompleteOrderWithReportById(
    private val repository: IOrderReportRepository
) {
    suspend fun complete(params: OrderCompleteReportParams): Response<Unit> =
        repository.completeOrder(params = params)

    suspend fun get(params: OrderReportParams): Response<OrderReport> =
        repository.getNetOrderReport(params = params)

}