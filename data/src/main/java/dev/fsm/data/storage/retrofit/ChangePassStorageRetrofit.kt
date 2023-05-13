package dev.fsm.data.storage.retrofit

import dev.fsm.data.network.api.ApiService
import dev.fsm.data.storage.IChangePassStorage
import dev.fsm.data.storage.models.params.user.ChangePassParams
import dev.fsm.data.utils.Mappers.map
import dev.fsm.data.utils.Request
import dev.fsm.data.utils.Response
import javax.inject.Inject

internal class ChangePassStorageRetrofit @Inject constructor(
    private val api: ApiService,
    private val request: Request
): IChangePassStorage {
    override suspend fun sendConfirmationCode(): Response<Unit> =
        request.safeApiCallWithAuth {
           TODO("Реализация отправки кода")
        }

    override suspend fun change(params: ChangePassParams): Response<Unit> =
        request.safeApiCallWithAuth {
            api.changePass(
                newPassword = params.newPassword
            )
        }.map {}
}