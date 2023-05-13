package dev.fsm.presentation.screens.orders.tablayout.contract

import com.ramcosta.composedestinations.spec.Direction
import dev.fsm.core.ViewEffect
import dev.fsm.core.ViewIntent
import dev.fsm.core.ViewState
import dev.fsm.presentation.screens.orders.models.Orders
import dev.fsm.presentation.screens.orders.tablayout.models.OrdersFilter

object OrdersContract {

    data class State(
        val actual: Actual,
        val new: New,
        val update: Update
    ) : ViewState

    sealed class New {
        object Idle : New()
        object Empty : New()
        object Loading : New()
        data class Loaded(val orders: List<Orders>) : New()
        data class Failure(val exception: Exception) : New()
    }

    sealed class Actual {
        object Idle : Actual()
        object Empty : Actual()
        object Loading : Actual()
        data class Loaded(val orders: List<Orders>) : Actual()
        data class Failure(val exception: Exception) : Actual()
    }

    sealed class Update {
        object Idle : Update()
        object Loading : Update()
    }

    sealed class Intent : ViewIntent {
        data class SetFilter(val params: OrdersFilter?) : Intent()
        data class OrderClick(val order: Orders) : Intent()
        object Update : Intent()
        object OpenDrawer : Intent()
    }

    sealed class Effect : ViewEffect {
        object OpenDrawer : Effect()
        data class NavigateTo(val direction: Direction) : Effect()
    }
}