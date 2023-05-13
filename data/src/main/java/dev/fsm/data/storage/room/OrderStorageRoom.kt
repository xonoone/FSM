package dev.fsm.data.storage.room

import dev.fsm.data.storage.IOrderStorage
import dev.fsm.data.database.dao.OrderDao
import dev.fsm.data.database.entities.Order
import dev.fsm.data.utils.Response
import dev.fsm.data.utils.safeCall
import javax.inject.Inject

class OrderStorageRoom @Inject constructor(
    private val orderDao: OrderDao
): IOrderStorage.IRoom {

    override suspend fun save(order: Order): Response<Unit> =
        safeCall { TODO() }

    override suspend fun get(): Response<Order> =
        safeCall { TODO() }

    override suspend fun clear(): Response<Unit> =
        safeCall { TODO() }
}