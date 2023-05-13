package dev.fsm.domain.repository

import dev.fsm.domain.models.token.Token
import dev.fsm.domain.utils.Response

interface ITokenRepository {

    suspend fun save(params: Token): Response<Boolean>
    suspend fun get(): Response<Token>
    suspend fun clear(): Boolean
}