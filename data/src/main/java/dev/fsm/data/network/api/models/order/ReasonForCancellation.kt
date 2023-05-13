package dev.fsm.data.network.api.models.order

import com.google.gson.annotations.SerializedName
import dev.fsm.domain.models.cancel.CancelOrderReason

data class ReasonForCancellation(
    @SerializedName("id"            ) val id   : String?  = null,
    @SerializedName("reasonName"    ) val name : String?  = null,
    @SerializedName("reasonNote"    ) val note : String?  = null
) {
    companion object {
        fun Collection<ReasonForCancellation>.toReasonForCancellation(): Collection<CancelOrderReason> =
            map { it.toReasonForCancellation() }
        fun ReasonForCancellation.toReasonForCancellation(): CancelOrderReason = CancelOrderReason(
            id = id       ?:  "",
            name = name   ?:  "",
            note = note
        )
    }
}
