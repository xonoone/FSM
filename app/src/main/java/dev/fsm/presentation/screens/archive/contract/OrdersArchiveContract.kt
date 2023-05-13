package dev.fsm.presentation.screens.archive.contract

import com.ramcosta.composedestinations.spec.Direction
import dev.fsm.core.ViewEffect
import dev.fsm.core.ViewIntent
import dev.fsm.core.ViewState
import dev.fsm.presentation.screens.orders.models.ArchiveFilter
import dev.fsm.presentation.screens.orders.models.Orders
import java.lang.Exception

object OrdersArchiveContract {

    data class State(
        val screenState: ScreenState,
        val updating: Boolean
    ) : ViewState

    sealed class ScreenState  {
        object Loading : ScreenState()
        data class Loaded(val data: List<Orders>) : ScreenState()
        data class Failure(val exception: Exception) : ScreenState()
    }

    sealed class Intent : ViewIntent {
        object Update : Intent()
        data class OrderClick(val orderId: String) : Intent()
        object GetArchiveOrders : Intent()
        data class SetFilter(val params: ArchiveFilter? = null) : Intent()
        object OpenDrawer : Intent()
    }

    sealed class Effect : ViewEffect {
        data class ShowErrorDialog(val exception: Exception) : Effect()
        data class Navigate(val direction: Direction) : Effect()
        object OpenDrawer : Effect()
    }
}