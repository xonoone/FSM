package dev.fsm.domain.usecase.token

import dev.fsm.domain.models.token.AuthenticationParams
import dev.fsm.domain.repository.IAuthenticationRepository
import dev.fsm.domain.utils.Response

class AuthenticationByLogin(
    private val repository: IAuthenticationRepository,
) {
    suspend fun auth(params: AuthenticationParams): Response<Unit> =
        repository.login(params = params)
}