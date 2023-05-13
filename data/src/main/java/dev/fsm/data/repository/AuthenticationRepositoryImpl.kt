package dev.fsm.data.repository

import dev.fsm.data.database.entities.Tokens
import dev.fsm.data.storage.models.params.tokens.AuthTokenParams.Companion.toAuthTokenParams
import dev.fsm.data.storage.retrofit.TokenStorageRetrofit
import dev.fsm.data.storage.room.TokenStorageRoom
import dev.fsm.data.utils.Mappers.toDomain
import dev.fsm.data.utils.Response.Error
import dev.fsm.domain.repository.IAuthenticationRepository
import dev.fsm.domain.utils.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import dev.fsm.data.utils.Response.Success.Data as Success
import dev.fsm.domain.models.token.AuthenticationParams as DomainAuthTokenParams

internal class AuthenticationRepositoryImpl @Inject constructor(
    private val network: TokenStorageRetrofit,
    private val database: TokenStorageRoom
) : IAuthenticationRepository {

    private val _isLoggedIn = MutableStateFlow(
        value = runBlocking {
            val authorizedResponse = isAuthorized()
            return@runBlocking authorizedResponse is Response.Success
        }
    )
    override val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    override suspend fun login(params: DomainAuthTokenParams): Response<Unit> {
        val response = network.auth(params = params.toAuthTokenParams())
        if (response is Success) {
            val saveResponse = database.save(tokens = Tokens.map(params = response.data))
            if (saveResponse is Error) {
                return Response.Error(exception = saveResponse.exception)
            }
            _isLoggedIn.update { true }
        }
        return response.toDomain()
    }

    override suspend fun logout(): Response<Unit> {
        _isLoggedIn.update { false }
        return database.clear().toDomain()
    }

    private suspend fun isAuthorized(): Response<Unit> {
        val response = database.get()
        return when (response) {
            is Error ->
                Response.Error(exception = response.exception)

            is dev.fsm.data.utils.Response.Success.Data ->
                Response.Success(Unit)

            is dev.fsm.data.utils.Response.Success.Empty ->
                Response.Error(exception = Exception("Saved params not found"))
        }
    }
}