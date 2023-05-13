package dev.fsm.presentation.screens.orders.tablayout.models

import android.os.Parcelable
import dev.fsm.domain.models.orders.OrdersFilterParams
import dev.fsm.domain.utils.IServerDateConverter.ServerDateConverter.toISODateTimeString
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrdersFilter(
    val date: FilterDate? = null,
    val isSortedEarly: Boolean? = null
) : Parcelable {
    companion object {
        fun OrdersFilter.toDomain(): OrdersFilterParams = OrdersFilterParams(
            statusCodes = listOf(),
            dateFrom = date?.dateFrom?.toISODateTimeString(),
            dateTo = date?.dateTo?.toISODateTimeString(),
            sortEarly = isSortedEarly ?: true
        )
    }
}