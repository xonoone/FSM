package dev.fsm.presentation.model.order

import android.os.Parcelable
import dev.fsm.presentation.model.order.OrderListItem.Companion.toOrderListItem
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
        fun OrderList.toOrderList(): DomainOrderList = DomainOrderList(
            id = id,
            name = name,
            isSingle = isSingle,
            values = values.map {
                it.toOrderListItem()
            }
        )

        fun DomainOrderList.toOrderList(): OrderList = OrderList(
            id = id,
            name = name,
            isSingle = isSingle,
            values = values.map {
                it.toOrderListItem()
            } as ArrayList<OrderListItem>
        )
    }
}