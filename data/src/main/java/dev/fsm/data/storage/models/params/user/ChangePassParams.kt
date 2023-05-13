package dev.fsm.data.storage.models.params.user

import dev.fsm.domain.models.password.ChangePassParams as DomainChangePassParams

data class ChangePassParams(
    val newPassword: String
) {
    companion object {
        fun DomainChangePassParams.toChangePassParams(): ChangePassParams = ChangePassParams(
            newPassword = newPassword
        )
    }
}