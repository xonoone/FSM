package dev.fsm.domain.models.report

data class ReportField(
    val id       : String,
    val name     : String,
    val type     : String,
    var value    : Any?    = null,
    val visible  : Boolean = false,
    val required : Boolean = false
)