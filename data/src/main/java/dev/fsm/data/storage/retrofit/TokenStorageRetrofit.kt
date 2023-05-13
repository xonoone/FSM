package dev.fsm.data.storage.retrofit

import android.util.Log
import dev.fsm.data.network.api.ApiService
import dev.fsm.data.network.api.models.user.authentication.Tokens
import dev.fsm.data.storage.IAuthenticationStorage
import dev.fsm.data.storage.models.params.tokens.AuthTokenParams
import dev.fsm.data.storage.models.params.tokens.RefreshAuthTokenParams
import dev.fsm.data.utils.Mappers.map
import dev.fsm.data.utils.Response
import dev.fsm.data.utils.safeApiCall
import javax.inject.Inject

internal class TokenStorageRetrofit @Inject constructor(
    private val api: ApiService
): IAuthenticationStorage.IRetrofit {

    override suspend fun auth(params: AuthTokenParams): Response<Tokens> =
        safeApiCall {
            api.authorization(
                login = params.login,
                password = params.password
            )
        }.map { it.data }

    override suspend fun refresh(params: RefreshAuthTokenParams): Response<Tokens> =
        safeApiCall {
            api.refreshToken(refreshToken = params.refreshToken)
        }.map { it.data }
}