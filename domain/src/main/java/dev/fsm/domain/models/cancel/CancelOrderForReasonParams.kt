package dev.fsm.domain.models.cancel

data class CancelOrderForReasonParams(
    val orderId     : String,
    val reasonId    : String,
    val note        : String? = null,
)
