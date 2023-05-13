package dev.fsm.data.repository

import dev.fsm.data.storage.models.params.user.ChangePassParams.Companion.toChangePassParams
import dev.fsm.data.storage.retrofit.ChangePassStorageRetrofit
import dev.fsm.data.utils.Mappers.toResponse
import dev.fsm.domain.models.password.ChangePassParams
import dev.fsm.domain.repository.IChangePassRepository
import dev.fsm.domain.utils.Response

internal class ChangePassRepositoryImpl(
    private val network: ChangePassStorageRetrofit
) : IChangePassRepository {
    override suspend fun sendConfirmationCode(): Response<Unit> {
        val response = network.sendConfirmationCode()
        return response.toResponse {}
    }

    override suspend fun change(params: ChangePassParams): Response<Unit> {
        val response = network.change(params = params.toChangePassParams())
        return response.toResponse {}
    }
}