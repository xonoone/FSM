package dev.fsm.data.repository

import dev.fsm.data.network.api.models.user.User.Companion.toUserDomain
import dev.fsm.data.storage.retrofit.UserStorageRetrofit
import dev.fsm.data.storage.room.UserStorageRoom
import dev.fsm.data.utils.Mappers.toResponse
import dev.fsm.domain.models.user.User
import dev.fsm.domain.repository.IUserRepository
import dev.fsm.domain.utils.Response
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val network: UserStorageRetrofit,
    private val database: UserStorageRoom
) : IUserRepository {
    override suspend fun getNetUserInfo(): Response<User> {
        val response = network.get()
        return response.toResponse { it.toUserDomain() }
    }

    override suspend fun getLocalUserInfo(): Response<User> {
        val response = database.get()
        return response.toResponse { it.toUserDomain() }
    }
}