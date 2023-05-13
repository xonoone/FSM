package dev.fsm.utils.statuses

import android.os.Parcelable
import androidx.annotation.StringRes
import dev.fsm.R
import dev.fsm.domain.utils.OrderStatusCodes
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class Statuses(val code: String, @StringRes val name: Int) : Parcelable {
    object New : Statuses(
        code = OrderStatusCodes.NEW,
        name = R.string.order_status_new
    )

    object Accepted : Statuses(
        code = OrderStatusCodes.ACCEPTED,
        name = R.string.order_status_accepted
    )

    object InRoad : Statuses(
        code = OrderStatusCodes.INROAD,
        name = R.string.order_status_inroad
    )

    object Active : Statuses(
        code = OrderStatusCodes.ACTIVE,
        name = R.string.order_status_active
    )

    object Rejected : Statuses(
        code = OrderStatusCodes.REJECTED,
        name = R.string.order_status_rejected
    )

    object Canceled : Statuses(
        code = OrderStatusCodes.CANCELED,
        name = R.string.order_status_canceled
    )

    object Completed : Statuses(
        code = OrderStatusCodes.COMPLETED,
        name = R.string.order_status_completed
    )

    object Suspend : Statuses(
        code = OrderStatusCodes.SUSPENDED,
        name = R.string.order_status_suspend
    )
}
