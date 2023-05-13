package dev.fsm.presentation.screens.orders.models

import android.os.Parcelable
import dev.fsm.domain.models.orders.OrdersFilterParams
import dev.fsm.domain.utils.IServerDateConverter.ServerDateConverter.toISODateTimeString
import dev.fsm.utils.statuses.Statuses
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalTime

@Parcelize
data class ArchiveFilter(
    val status: Statuses? = null,
    val dateFrom: LocalDate? = null,
    val dateTo: LocalDate? = null,
    val isSortedEarly: Boolean? = null
) : Parcelable {
    companion object {
        fun ArchiveFilter.toDomain(): OrdersFilterParams = OrdersFilterParams(
            statusCodes = this.status?.code?.let { listOf(it) } ?: listOf(),
            dateFrom = this.dateFrom?.atStartOfDay()?.toISODateTimeString(),
            dateTo = this.dateTo?.atTime(LocalTime.MAX)?.toISODateTimeString(),
            sortEarly = this.isSortedEarly
        )
    }
}