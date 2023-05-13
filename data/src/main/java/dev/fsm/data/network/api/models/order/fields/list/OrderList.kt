package dev.fsm.data.network.api.models.order.fields.list

import com.google.gson.annotations.SerializedName
import dev.fsm.data.network.api.models.order.fields.list.OrderListItem.Companion.toOrderListItem
import dev.fsm.domain.models.order.fields.list.OrderList as DomainOrderList
import dev.fsm.domain.models.order.fields.list.OrderListItem as DomainOrderListItem

data class OrderList(
    @SerializedName("id"                ) val id             : String?              = null,
    @SerializedName("name"              ) val name           : String?              = null,
    @SerializedName("isSingle"          ) val isSingle       : Boolean?             = null,
    @SerializedName("items"             ) val values         : List<OrderListItem>? = listOf()
) {
    companion object {
        fun OrderList.toOrderList(): DomainOrderList = DomainOrderList(
            id = id ?: throw NullPointerException("ID not found"),
            name = name ?: "",
            isSingle = isSingle ?: false,
            values = values?.map { it.toOrderListItem() } ?: emptyList()
        )

        fun DomainOrderList.toOrderList(): OrderList = OrderList(
            id = id,
            name = name,
            isSingle = isSingle,
            values = values.map { it.toOrderListItem() }
        )

        private fun DomainOrderListItem.toOrderListItem(): OrderListItem = OrderListItem(
            id = id,
            name = name,
            isSelected = isSelected
        )
    }
}
