package dev.fsm.data.storage.models.params.order

import dev.fsm.data.network.api.models.order.fields.OrderCompleteField
import dev.fsm.data.network.api.models.order.fields.OrderCompleteField.Companion.toOrderCompleteField
import dev.fsm.domain.models.report.OrderCompleteReportParams as DomainOrderCompleteReportParams

data class OrderCompleteReportParams(
    val orderId       :  String,
    val reportFields  :  List<OrderCompleteField> = listOf()
) {
    companion object {
        fun DomainOrderCompleteReportParams.toOrderCompleteReportParams(): OrderCompleteReportParams = OrderCompleteReportParams(
            orderId = orderId,
            reportFields = reportFields.map { it.toOrderCompleteField() }
        )
    }
}