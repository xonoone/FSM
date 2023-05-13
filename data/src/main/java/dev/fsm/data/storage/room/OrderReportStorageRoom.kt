package dev.fsm.data.storage.room

import dev.fsm.data.network.api.models.order.fields.OrderField
import dev.fsm.data.storage.IOrderReportStorage
import dev.fsm.data.storage.models.params.order.OrderReportParams
import dev.fsm.data.database.dao.OrderReportDao
import dev.fsm.data.network.api.models.order.OrderReport
import dev.fsm.data.utils.Response
import dev.fsm.data.utils.safeCall
import javax.inject.Inject

internal class OrderReportStorageRoom @Inject constructor(
    private val orderDao: OrderReportDao
): IOrderReportStorage.IRoom {
    override suspend fun saveOrder(order: ArrayList<OrderField>): Response<Unit> =
        safeCall {
//            orderDao.clear()
//            orderDao.save(order = order.map())
        }

    override suspend fun getOrder(params: OrderReportParams): Response<OrderReport> =
        TODO("Как сохранить список")
}