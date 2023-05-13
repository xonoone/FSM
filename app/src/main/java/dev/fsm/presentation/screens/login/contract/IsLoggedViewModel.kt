package dev.fsm.presentation.screens.login.contract

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.fsm.domain.repository.IAuthenticationRepository
import javax.inject.Inject

@HiltViewModel
class IsLoggedViewModel @Inject constructor(
    repository: IAuthenticationRepository
) : ViewModel() {

    val isLoggedFlow = repository.isLoggedIn
    val isLogged get() = isLoggedFlow.value
}

