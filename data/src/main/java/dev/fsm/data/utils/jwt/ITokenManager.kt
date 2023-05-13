package dev.fsm.data.utils.jwt

import dev.fsm.data.database.entities.Tokens
import dev.fsm.data.storage.models.params.tokens.RefreshAuthTokenParams
import dev.fsm.data.storage.retrofit.TokenStorageRetrofit
import dev.fsm.data.storage.room.TokenStorageRoom
import dev.fsm.data.utils.Response
import dev.fsm.error.exceptions.jwt.Unauthorized
import javax.inject.Inject

internal interface ITokenManager {
    suspend fun refresh(): TokenResponse

    class TokenManager @Inject constructor(
        private val apiStorage: TokenStorageRetrofit,
        private val localStorage: TokenStorageRoom
    ) : ITokenManager {

        override suspend fun refresh(): TokenResponse {
            val response = localStorage.get()
            val token = when (response) {
                is Response.Error ->
                    return TokenResponse.Failure(exception = response.exception)
                is Response.Success.Data ->
                    response.data.refreshToken
                is Response.Success.Empty ->
                    return TokenResponse.Failure(exception = Unauthorized())
            }
            val refreshResponse =
                apiStorage.refresh(params = RefreshAuthTokenParams(refreshToken = token))
            return when (refreshResponse) {
                is Response.Error ->
                    TokenResponse.Failure(exception = refreshResponse.exception)
                is Response.Success.Data -> {
                    val saveResponse = localStorage.save(tokens = Tokens.map(params = refreshResponse.data))
                    when (saveResponse) {
                        is Response.Error ->
                            TokenResponse.Failure(exception = saveResponse.exception)
                        is Response.Success ->
                            TokenResponse.Success
                    }
                }
                is Response.Success.Empty ->
                    TokenResponse.Failure(exception = Unauthorized())
            }
        }
    }
}