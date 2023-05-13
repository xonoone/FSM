package dev.fsm.presentation.screens.user.info.contract

import com.ramcosta.composedestinations.spec.Direction
import dev.fsm.core.ViewEffect
import dev.fsm.core.ViewIntent
import dev.fsm.core.ViewState
import dev.fsm.presentation.screens.user.info.models.User
import java.lang.Exception

object UserContract {

    sealed class State : ViewState {
        object Idle : State()
        object Loading : State()
        data class Loaded(val data: User) : State()
        data class Failure(val exception: Exception) : State()
    }

    sealed class Intent : ViewIntent {
        object ChangePassword : Intent()
        object Logout : Intent()
        object OpenDrawer : Intent()
    }

    sealed class Effect : ViewEffect {
        data class ShowErrorDialog(val exception: Exception) : Effect()
        object OpenDrawer : Effect()

        data class Navigate(val direction: Direction) : Effect()
        data class NavigateBackStack(val direction: Direction) : Effect()
    }
}