package dev.fsm.domain.repository

import dev.fsm.domain.models.user.User
import dev.fsm.domain.utils.Response

interface IUserRepository {
    suspend fun getNetUserInfo(): Response<User>
    suspend fun getLocalUserInfo(): Response<User>
}