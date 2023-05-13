package dev.fsm.presentation.screens.user.change.contract

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.fsm.core.BaseViewModel
import dev.fsm.domain.repository.IAuthenticationRepository
import dev.fsm.domain.usecase.password.ChangePassword
import dev.fsm.domain.utils.Response
import dev.fsm.presentation.screens.user.change.models.ChangePassParams
import dev.fsm.presentation.screens.user.change.models.ChangePassParams.Companion.toChangePassParams
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePassViewModel @Inject constructor(
    private val password: ChangePassword,
    private val auth: IAuthenticationRepository
): BaseViewModel<ChangePassContract.State, ChangePassContract.Intent, ChangePassContract.Effect>() {

    override fun createInitialState(): ChangePassContract.State = ChangePassContract.State.Idle

    override fun handleIntent(event: ChangePassContract.Intent) {
        when(event) {
            is ChangePassContract.Intent.ChangePassword -> changePassword(params = event.params)
        }
    }

    private fun changePassword(params: ChangePassParams) {
        viewModelScope.launch(dispatcher) {
            setState { ChangePassContract.State.Loading }
            when (val response = password.changePassword(params = params.toChangePassParams())) {
                is Response.Success -> {
                    setEffect { ChangePassContract.Effect.ShowToast(message = "Вы успешно сменили пароль!") }
                    auth.logout()
                }
                is Response.Error -> setEffect {
                    ChangePassContract.Effect.ShowErrorDialog(exception = response.exception)
                }
            }
        }
    }
}