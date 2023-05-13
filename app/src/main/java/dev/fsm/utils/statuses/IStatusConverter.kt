package dev.fsm.utils.statuses

import androidx.annotation.StringRes
import dev.fsm.utils.statuses.Statuses.New
import dev.fsm.utils.statuses.Statuses.Accepted
import dev.fsm.utils.statuses.Statuses.Active
import dev.fsm.utils.statuses.Statuses.InRoad
import dev.fsm.utils.statuses.Statuses.Rejected
import dev.fsm.utils.statuses.Statuses.Canceled
import dev.fsm.utils.statuses.Statuses.Completed
import dev.fsm.utils.statuses.Statuses.Suspend

interface IStatusConverter {

    @StringRes fun codeToName(statusCode: String): Int?
    fun codeToStatus(statusCode: String): Statuses?

    object StatusConverter: IStatusConverter {

        @StringRes
        override fun codeToName(statusCode: String): Int? = when (statusCode) {
            New.code -> New.name
            Accepted.code -> Accepted.name
            InRoad.code -> InRoad.name
            Active.code -> Active.name
            Rejected.code -> Rejected.name
            Canceled.code -> Canceled.name
            Completed.code -> Completed.name
            Suspend.code -> Suspend.name
            else -> null
        }

        override fun codeToStatus(statusCode: String): Statuses? = when (statusCode) {
            New.code -> New
            Accepted.code -> Accepted
            InRoad.code -> InRoad
            Active.code -> Active
            Rejected.code -> Rejected
            Canceled.code -> Canceled
            Completed.code -> Completed
            Suspend.code -> Suspend
            else -> null
        }
    }
}