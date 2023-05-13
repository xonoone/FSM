package dev.fsm.presentation.screens.user.change.contract

import dev.fsm.core.ViewEffect
import dev.fsm.core.ViewIntent
import dev.fsm.core.ViewState
import dev.fsm.presentation.screens.user.change.models.ChangePassParams

object ChangePassContract {

    sealed class State : ViewState {
        object Idle : State()
        object Loading : State()
        object Success : State()
        data class Failure(val exception: Exception) : State()
    }

    sealed class Intent : ViewIntent {
        data class ChangePassword(val params: ChangePassParams) : Intent()
    }

    sealed class Effect : ViewEffect {
        data class ShowToast(val message: String) : Effect()
        data class ShowErrorDialog(val exception: Exception) : Effect()
    }
}