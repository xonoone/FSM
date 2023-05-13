package dev.fsm.domain.models.report

data class OrderReport(
    val orderId      : String,
    val name         : String,
    val description  : String,
    val reportFields : ArrayList<ReportField> = arrayListOf()
)