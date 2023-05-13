package dev.fsm.presentation.screens.user.info.contract

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.fsm.core.BaseViewModel
import dev.fsm.destinations.ChangePassScreenDestination
import dev.fsm.domain.repository.IAuthenticationRepository
import dev.fsm.domain.usecase.user.FetchUserInfoByPage
import dev.fsm.domain.utils.Response
import dev.fsm.presentation.screens.user.info.models.User.Companion.toUser
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val user: FetchUserInfoByPage,
    private val auth: IAuthenticationRepository
) : BaseViewModel<UserContract.State, UserContract.Intent, UserContract.Effect>() {

    override fun createInitialState(): UserContract.State = UserContract.State.Loading

    override fun handleIntent(event: UserContract.Intent) {
        when (event) {
            is UserContract.Intent.ChangePassword -> setEffect {
                UserContract.Effect.Navigate(direction = ChangePassScreenDestination)
            }
            is UserContract.Intent.Logout -> {
                viewModelScope.launch(dispatcher) {
                    auth.logout()
                }
            }
            is UserContract.Intent.OpenDrawer -> setEffect { UserContract.Effect.OpenDrawer }
        }
    }

    init { getUser() }

    private fun getUser() {
        viewModelScope.launch(dispatcher) {
            setState { UserContract.State.Loading }
            val response = user.getUser()
            when (response) {
                is Response.Success -> {
                    val data = response.data
                    if (data != null) {
                        setState {
                            UserContract.State.Loaded(data = data.toUser())
                        }
                    } else {
                        setEffect {
                            UserContract.Effect.ShowErrorDialog(exception = NoSuchElementException())
                        }
                    }
                }
                is Response.Error -> setEffect {
                    UserContract.Effect.ShowErrorDialog(exception = response.exception)
                }
            }
        }
    }
}