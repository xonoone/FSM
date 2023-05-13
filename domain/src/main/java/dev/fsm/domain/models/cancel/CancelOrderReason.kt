package dev.fsm.domain.models.cancel

data class CancelOrderReason(
    val id   : String  = "",
    val name : String  = "",
    val note : String? = null
)