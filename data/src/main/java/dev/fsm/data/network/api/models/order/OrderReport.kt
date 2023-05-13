package dev.fsm.data.network.api.models.order

import dev.fsm.data.network.api.models.order.fields.OrderField
import dev.fsm.data.network.api.models.order.fields.OrderField.Companion.toReportField
import dev.fsm.domain.models.report.ReportField
import dev.fsm.domain.models.report.OrderReport as DomainOrderReport

data class OrderReport(
    val orderId         :  String,
    val name            :  String,
    val description     :  String,
    val reportFields    :  List<OrderField> = listOf()
) {
    companion object {
        fun OrderReport.toOrderReport(): DomainOrderReport = DomainOrderReport(
            orderId = orderId,
            name = name,
            description = description,
            reportFields = reportFields.toReportField() as ArrayList<ReportField>
        )
    }
}
