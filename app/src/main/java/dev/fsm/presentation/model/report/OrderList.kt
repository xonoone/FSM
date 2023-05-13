package dev.fsm.presentation.model.report

import android.os.Parcelable
import dev.fsm.presentation.model.report.OrderListItem.Companion.toOrderReportListItem
import kotlinx.parcelize.Parcelize
import dev.fsm.domain.models.order.fields.list.OrderList as DomainOrderList

@Parcelize
data class OrderList(
    val id: String,
    val name: String,
    val isSingle: Boolean = false,
    val values: ArrayList<OrderListItem> = arrayListOf(),
) : Parcelable {
    companion object {
        fun OrderList.toOrderListReport(): DomainOrderList = DomainOrderList(
            id = id,
            name = name,
            isSingle = isSingle,
            values = values.map { it.toOrderReportListItem() }
        )

        fun DomainOrderList.toOrderListReport(): OrderList = OrderList(
            id = id,
            name = name,
            isSingle = isSingle,
            values = values.map { it.toOrderReportListItem() } as ArrayList<OrderListItem>
        )
    }
}