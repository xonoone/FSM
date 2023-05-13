package dev.fsm.data.storage.retrofit

import dev.fsm.data.network.api.ApiService
import dev.fsm.data.network.api.models.user.User
import dev.fsm.data.storage.IUserStorage
import dev.fsm.data.utils.Mappers.map
import dev.fsm.data.utils.Request
import dev.fsm.data.utils.Response
import javax.inject.Inject

internal class UserStorageRetrofit @Inject constructor(
    private val api: ApiService,
    private val request: Request
) : IUserStorage.IRetrofit {

    override suspend fun get(): Response<User> =
        request.safeApiCallWithAuth { api.getUser() }.map { it }

}