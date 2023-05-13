package dev.fsm.data.storage.room

import dev.fsm.data.storage.IUserStorage
import dev.fsm.data.database.dao.UserDao
import dev.fsm.data.network.api.models.user.User
import dev.fsm.data.utils.Response
import dev.fsm.data.utils.safeCall
import javax.inject.Inject

class UserStorageRoom @Inject constructor(
    private val userDao: UserDao
) : IUserStorage.IRoom {

    override suspend fun get(): Response<User> = TODO()

    override suspend fun save(user: User): Response<Unit> =
        safeCall {
            userDao.clear()
//            userDao.save(user = user.toUserDatabase())
        }

    override suspend fun clear(): Response<Unit> = safeCall { userDao.clear() }
}