package dev.fsm.data.storage

import dev.fsm.data.database.entities.Tokens as DBTokens
import dev.fsm.data.network.api.models.user.authentication.Tokens as DataTokens
import dev.fsm.data.storage.models.params.tokens.AuthTokenParams
import dev.fsm.data.storage.models.params.tokens.RefreshAuthTokenParams
import dev.fsm.data.utils.Response

sealed interface IAuthenticationStorage {

    interface IRetrofit {
        suspend fun auth(params: AuthTokenParams): Response<DataTokens>
        suspend fun refresh(params: RefreshAuthTokenParams): Response<DataTokens>
    }

    interface IRoom {
        suspend fun get(): Response<DBTokens>
        suspend fun save(tokens: DBTokens): Response<Unit>
        suspend fun clear(): Response<Unit>
    }
}