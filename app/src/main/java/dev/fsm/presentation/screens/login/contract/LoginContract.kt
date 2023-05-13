package dev.fsm.presentation.screens.login.contract

import dev.fsm.core.ViewEffect
import dev.fsm.core.ViewIntent
import dev.fsm.core.ViewState
import dev.fsm.domain.models.token.AuthenticateParams

object LoginContract {

    sealed class State : ViewState {
        object Idle : State()
        object Loading : State()
        object Success : State()
        data class Failure(val exception: Exception) : State()
    }

    sealed class Intent : ViewIntent {
        data class Authenticate(val params: AuthenticateParams) : Intent()
        data class ShowError(val exception: Exception) : Intent()
    }

    sealed class Effect : ViewEffect {
        data class ShowErrorDialog(val exception: Exception) : Effect()
    }
}