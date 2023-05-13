package dev.fsm.domain.models.report

data class OrderCompleteReportParams    (
    val orderId      : String,
    val reportFields : List<OrderCompleteField>
)
