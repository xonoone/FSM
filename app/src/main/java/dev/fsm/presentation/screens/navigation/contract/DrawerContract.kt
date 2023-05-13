package dev.fsm.presentation.screens.navigation.contract

import dev.fsm.core.ViewEffect
import dev.fsm.core.ViewIntent
import dev.fsm.core.ViewState

object DrawerContract {

    data class State(
        val user: User,
        val notifications: Notifications
    ) : ViewState

    sealed class Effect : ViewEffect

    sealed class Intent : ViewIntent {
        object UpdateNotifications : Intent()
        object UpdateUser : Intent()
    }

    sealed class User {
        object Loading : User()
        data class Loaded(val data: dev.fsm.presentation.screens.navigation.contract.User) : User()
        data class Failure(val exception: Exception) : User()
    }

    sealed class Notifications {
        object Loading : Notifications()
        data class Loaded(val count: Int) : Notifications()
        data class Failure(val exception: Exception) : Notifications()
    }
}