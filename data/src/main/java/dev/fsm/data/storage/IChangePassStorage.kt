package dev.fsm.data.storage

import dev.fsm.data.storage.models.params.user.ChangePassParams
import dev.fsm.data.utils.Response

interface IChangePassStorage {

    suspend fun sendConfirmationCode(): Response<Unit>
    suspend fun change(params: ChangePassParams): Response<Unit>
}