package dev.fsm.data.network.api.models.order.fields.list

import com.google.gson.annotations.SerializedName
import dev.fsm.domain.models.order.fields.list.OrderListItem as DomainOrderListItem

data class OrderListItem(
    @SerializedName("id"       ) val id         : String?  = null,
    @SerializedName("value"    ) val name       : String?  = null,
    @SerializedName("isChecked") val isSelected : Boolean? = null
) {
    companion object {
        fun OrderListItem.toOrderListItem(): DomainOrderListItem = DomainOrderListItem(
            id = id ?: throw NullPointerException("ID not found"),
            name = name ?: "",
            isSelected = isSelected ?: false
        )
    }
}
