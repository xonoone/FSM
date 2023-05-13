package dev.fsm.presentation.screens.login.contract

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.fsm.core.BaseViewModel
import dev.fsm.domain.models.token.AuthenticateParams
import dev.fsm.domain.models.token.AuthenticateParams.Companion.toDomain
import dev.fsm.domain.usecase.token.AuthenticationByLogin
import dev.fsm.domain.utils.Response
import dev.fsm.error.exceptions.ui.FieldsEmpty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: AuthenticationByLogin,
) : BaseViewModel<LoginContract.State, LoginContract.Intent, LoginContract.Effect>() {

    override fun createInitialState(): LoginContract.State = LoginContract.State.Idle

    override fun handleIntent(event: LoginContract.Intent) {
        when (event) {
            is LoginContract.Intent.Authenticate -> authentication(params = event.params)
            is LoginContract.Intent.ShowError -> setState { LoginContract.State.Failure(exception = event.exception) }
        }
    }

    private fun authentication(params: AuthenticateParams) {
        viewModelScope.launch(dispatcher) {
            setState { LoginContract.State.Loading }
            when (val response = useCase.auth(params = params.toDomain())) {
                is Response.Success -> setState {
                    LoginContract.State.Success
                }
                is Response.Error -> setState {
                    LoginContract.State.Failure(exception = response.exception)
                }
            }
        }
    }
}