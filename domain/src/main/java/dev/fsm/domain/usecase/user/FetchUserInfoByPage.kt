package dev.fsm.domain.usecase.user

import dev.fsm.domain.models.user.User
import dev.fsm.domain.repository.IUserRepository
import dev.fsm.domain.utils.Response
class FetchUserInfoByPage(
    private val repository: IUserRepository
) {
    suspend fun getUser(): Response<User> {
        return repository.getNetUserInfo()
    }
}