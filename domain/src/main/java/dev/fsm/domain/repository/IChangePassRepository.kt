package dev.fsm.domain.repository

import dev.fsm.domain.models.password.ChangePassParams
import dev.fsm.domain.utils.Response

interface IChangePassRepository {

    suspend fun sendConfirmationCode(): Response<Unit>
    suspend fun change(params: ChangePassParams): Response<Unit>
}