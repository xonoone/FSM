package dev.fsm.domain.models.order.fields

data class OrderField(
    val id       : String  = "",
    val name     : String  = "",
    val type     : String  = "",
    val value    : Any?    = null,
    val visible  : Boolean = false,
    val required : Boolean = false
)