package dev.fsm.data.storage.models.params.order

import dev.fsm.domain.models.cancel.CancelOrderForReasonParams as DomainCancelOrderForReasonParams
data class CancelOrderForReasonParams(
    val orderId     :   String,
    val reasonId    :   String,
    val note        :   String = ""
) {
    companion object {
        fun DomainCancelOrderForReasonParams.toDomainCancelOrderForReasonParams(): CancelOrderForReasonParams = CancelOrderForReasonParams(
            orderId = orderId,
            reasonId = reasonId,
            note = note ?: ""
        )
    }
}