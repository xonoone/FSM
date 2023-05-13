package dev.fsm.presentation.screens.order.info.models

import android.os.Parcelable
import dev.fsm.domain.models.cancel.CancelOrderReason as DomainCancelOrderReason
import kotlinx.parcelize.Parcelize

@Parcelize
data class CancelOrderReason(
    val id   : String  = "",
    val name : String  = "",
    val note : String? = null
): Parcelable {
    companion object {
        fun DomainCancelOrderReason.toCancelOrderReason(): CancelOrderReason = CancelOrderReason(
            id = id,
            name = name,
            note = note
        )
    }
}
