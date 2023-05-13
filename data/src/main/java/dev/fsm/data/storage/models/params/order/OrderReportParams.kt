package dev.fsm.data.storage.models.params.order

import dev.fsm.domain.models.report.OrderReportParams as DomainOrderReportParams
data class OrderReportParams(
    val orderId   :  String
){
    companion object {
        fun DomainOrderReportParams.toOrderReportParams(): OrderReportParams = OrderReportParams(orderId = orderId)
    }
}