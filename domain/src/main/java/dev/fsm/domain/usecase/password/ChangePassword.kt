package dev.fsm.domain.usecase.password

import dev.fsm.domain.models.password.ChangePassParams
import dev.fsm.domain.repository.IChangePassRepository
import dev.fsm.domain.utils.Response

class ChangePassword(
    private val repository: IChangePassRepository
) {
    suspend fun changePassword(params: ChangePassParams): Response<Unit> =
        repository.change(params = params)
}