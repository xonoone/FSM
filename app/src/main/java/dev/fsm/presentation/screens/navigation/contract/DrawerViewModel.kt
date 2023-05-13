package dev.fsm.presentation.screens.navigation.contract

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.fsm.core.BaseViewModel
import dev.fsm.domain.models.orders.Orders
import dev.fsm.domain.repository.IAuthenticationRepository
import dev.fsm.domain.usecase.orders.FetchNewOrdersByPage
import dev.fsm.domain.usecase.user.FetchUserInfoByPage
import dev.fsm.domain.utils.Response
import dev.fsm.presentation.screens.navigation.contract.DrawerContract.Effect
import dev.fsm.presentation.screens.navigation.contract.DrawerContract.Intent
import dev.fsm.presentation.screens.navigation.contract.DrawerContract.Notifications
import dev.fsm.presentation.screens.navigation.contract.DrawerContract.State
import dev.fsm.presentation.screens.navigation.contract.DrawerContract.User
import dev.fsm.presentation.screens.navigation.contract.User.Companion.toUser
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawerViewModel @Inject constructor(
    private val userUseCase: FetchUserInfoByPage,
    private val ordersUseCase: FetchNewOrdersByPage,
    private val repository: IAuthenticationRepository
) : BaseViewModel<State, Intent, Effect>() {

    override fun createInitialState(): State = State(
        user = User.Loading,
        notifications = Notifications.Loading
    )

    override fun handleIntent(event: Intent) {
        when (event) {
            is Intent.UpdateNotifications -> updateNotifications()
            is Intent.UpdateUser -> updateUser()
        }
    }

    init {
        subscribeToLoginChange()
    }

    private fun getDrawerInfo() {
        viewModelScope.launch(dispatcher) {
            setState {
                copy(
                    user = User.Loading,
                    notifications = Notifications.Loading
                )
            }
            val userResponse = async { userUseCase.getUser() }
            val orderResponse = async { ordersUseCase.get() }

            val waitedUser = userResponse.await()
            setState {
                copy(
                    user = waitedUser.toState()
                )
            }
            val waitedOrders = orderResponse.await()
            setState {
                copy(
                    notifications = waitedOrders.toState()
                )
            }
        }
    }

    private fun updateUser() {
        viewModelScope.launch(dispatcher) {
            setState { copy(user = User.Loading) }
            val userResponse = userUseCase.getUser()
            setState { copy(user = userResponse.toState()) }
        }
    }

    private fun updateNotifications() {
        viewModelScope.launch(dispatcher) {
            setState { copy(notifications = Notifications.Loading) }
            val orderResponse = ordersUseCase.get()
            setState { copy(notifications = orderResponse.toState()) }
        }
    }

    private fun subscribeToLoginChange() {
        viewModelScope.launch(dispatcher) {
            repository.isLoggedIn.collect { isLogged ->
                if (isLogged) {
                    getDrawerInfo()
                } else {
                    setState {
                        copy(
                            user = User.Loading,
                            notifications = Notifications.Loading
                        )
                    }
                }
            }
        }
    }

    private fun Response<dev.fsm.domain.models.user.User>.toState(): User = when (this) {
        is Response.Error -> User.Failure(exception = exception)
        is Response.Success -> data?.let { userInfo ->
            User.Loaded(data = userInfo.toUser())
        } ?: User.Failure(exception = NullPointerException())
    }

    private fun Response<List<Orders>>.toState(): Notifications = when (this) {
        is Response.Error -> Notifications.Failure(exception = exception)
        is Response.Success -> Notifications.Loaded(
            count = data?.filter { !it.isChecked }?.size ?: 0
        )
    }
}

data class User(
    val imageUrl: String? = null,
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val login: String
) {
    companion object {
        fun dev.fsm.domain.models.user.User.toUser() = User(
            imageUrl = photo?.url,
            firstName = firstName,
            lastName = lastName,
            middleName = middleName,
            login = login
        )
    }
}