package dev.fsm.presentation.screens.user.change.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import dev.fsm.domain.models.password.ChangePassParams as DomainChangePassParams

@Parcelize
data class ChangePassParams(
    val newPassword     : String
): Parcelable {
    companion object {
        fun ChangePassParams.toChangePassParams(): DomainChangePassParams = DomainChangePassParams(
            newPassword = newPassword
        )
    }
}
