package dev.fsm.presentation.screens.orders.tablayout.models

import android.os.Parcelable
import androidx.annotation.StringRes
import dev.fsm.R
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
sealed class FilterDate(
    @StringRes val name: Int,
    val dateFrom: LocalDateTime?,
    val dateTo: LocalDateTime?,
) : Parcelable {

    object Today : FilterDate(
        name = R.string.filter_param_today,
        dateFrom = LocalDateTime.now().atDayStart(),
        dateTo = LocalDateTime.now().atDayEnd()
    )

    object Tomorrow : FilterDate(
        name = R.string.filter_param_tomorrow,
        dateFrom = LocalDateTime.now().plusDays(1).atDayStart(),
        dateTo = LocalDateTime.now().plusDays(1).atDayEnd()
    )

    object Week : FilterDate(
        name = R.string.filter_param_week,
        dateFrom = LocalDateTime.now().atDayStart(),
        dateTo = LocalDateTime.now().plusDays(6).atDayEnd()
    )

    data class Period(
        private val periodFrom: LocalDateTime? = null,
        private val periodTo: LocalDateTime? = null,
    ) : FilterDate(
        name = R.string.filter_param_period,
        dateFrom = periodFrom?.withNano(0),
        dateTo = periodTo?.withNano(0)
    ) {
        class Builder {
            var from: LocalDateTime? = null
            var to: LocalDateTime? = null

            fun setDateFrom(dateFrom: LocalDateTime? = null): Builder {
                from = dateFrom
                return this
            }

            fun setDateTo(dateTo: LocalDateTime? = null): Builder {
                to = dateTo
                return this
            }

            fun build(): Period = Period(periodFrom = from, periodTo = to)
        }
    }
}

private fun LocalDateTime.atDayStart(): LocalDateTime =
    this.withHour(0).withMinute(0).withSecond(0).withNano(0)

private fun LocalDateTime.atDayEnd(): LocalDateTime =
    this.withHour(23).withMinute(59).withSecond(59).withNano(0)