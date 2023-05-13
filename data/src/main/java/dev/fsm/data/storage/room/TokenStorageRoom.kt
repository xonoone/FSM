package dev.fsm.data.storage.room

import dev.fsm.data.database.dao.TokenDao
import dev.fsm.data.database.entities.Tokens
import dev.fsm.data.storage.IAuthenticationStorage
import dev.fsm.data.utils.Response
import dev.fsm.data.utils.safeCall
import javax.inject.Inject

class TokenStorageRoom @Inject constructor(
    private val tokenDao: TokenDao
) : IAuthenticationStorage.IRoom {
    override suspend fun get(): Response<Tokens> = safeCall {
        tokenDao.get()
    }

    override suspend fun save(tokens: Tokens): Response<Unit> = safeCall {
        tokenDao.clear()
        tokenDao.save(tokens = tokens)
    }

    override suspend fun clear(): Response<Unit> = safeCall {
        tokenDao.clear()
    }

}