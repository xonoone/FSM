package dev.fsm.domain.repository

import dev.fsm.domain.models.token.AuthenticationParams
import dev.fsm.domain.utils.Response
import kotlinx.coroutines.flow.StateFlow

interface IAuthenticationRepository {

    val isLoggedIn: StateFlow<Boolean>

    suspend fun login(params: AuthenticationParams): Response<Unit>
    suspend fun logout(): Response<Unit>
}