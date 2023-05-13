package dev.fsm.presentation.model.order

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import dev.fsm.domain.models.order.fields.list.OrderListItem as DomainOrderListItem

@Parcelize
data class OrderListItem(
    val id: String,
    val name: String,
    var isChecked: Boolean = false
) : Parcelable {
    companion object {
        fun OrderListItem.toOrderListItem(): DomainOrderListItem = DomainOrderListItem(
            id = id,
            name = name,
            isSelected = isChecked
        )

        fun DomainOrderListItem.toOrderListItem(): OrderListItem = OrderListItem(
            id = id,
            name = name,
            isChecked = isSelected
        )
    }
}