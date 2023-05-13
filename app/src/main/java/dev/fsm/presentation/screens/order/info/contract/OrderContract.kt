package dev.fsm.presentation.screens.order.info.contract

import com.ramcosta.composedestinations.spec.Direction
import com.vanpra.composematerialdialogs.MaterialDialogState
import dev.fsm.core.ViewEffect
import dev.fsm.core.ViewIntent
import dev.fsm.core.ViewState
import dev.fsm.presentation.screens.order.info.models.CancelOrderReason
import dev.fsm.presentation.screens.order.info.models.OrderInfo
import dev.fsm.utils.statuses.Statuses

object OrderContract {

    sealed class Order {
        object Loading : Order()
        data class Loaded(val order: OrderInfo) : Order()
        data class Failure(val exception: Exception) : Order()
    }

    sealed class CancelOrder {
        object Idle : CancelOrder()
        object Loading : CancelOrder()
    }

    sealed class GetCancelReasons {
        object Loading : GetCancelReasons()
        data class Success(val data: List<CancelOrderReason>) : GetCancelReasons()
        data class Failure(val exception: Exception) : GetCancelReasons()
    }

    data class State(
        val order: Order,
        val getCancelReasons: GetCancelReasons,
        val cancelOrder: CancelOrder
    ) : ViewState

    sealed class Effect : ViewEffect {
        data class ShowErrorDialog(val exception: Exception) : Effect()
        data class ShowToast(val message: String) : Effect()
        object ShowRejectDialog : Effect()
        object ShowReasonsDialog : Effect()
        data class Navigate(val direction: Direction) : Effect()
        data class NavigateWithPopUp(val direction: Direction) : Effect()
        data class NavigateBackStack(val direction: Direction) : Effect()
    }

    sealed class Intent : ViewIntent {
        data class GetOrder(val id: String) : Intent()
        data class RejectOrder(val id: String) : Intent()
        object OnBackClick : Intent()
        data class CancelOrder(
            val orderId: String,
            val reasonId: String,
            val note: String?
        ) : Intent()

        data class PositiveClick(
            val orderId: String,
            val currentStatus: Statuses
        ) : Intent()

        data class NegativeClick(
            val orderId: String,
            val currentStatus: Statuses,
            val dialogState: MaterialDialogState? = null
        ) : Intent()
    }
}