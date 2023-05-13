package dev.fsm.data.storage

import dev.fsm.data.network.api.models.user.User
import dev.fsm.data.utils.Response

sealed interface IUserStorage {
    suspend fun get(): Response<User>
    interface IRetrofit : IUserStorage
    interface IRoom : IUserStorage {
        suspend fun save(user: User): Response<Unit>
        suspend fun clear(): Response<Unit>
    }
}