package dev.fsm.presentation.screens.login.view

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection.Companion.Content
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.fsm.R
import dev.fsm.destinations.LoginScreenDestination
import dev.fsm.destinations.OrdersScreenDestination
import dev.fsm.domain.models.token.AuthenticateParams
import dev.fsm.error.exceptions.ui.FieldsEmpty
import dev.fsm.presentation.screens.login.contract.LoginContract
import dev.fsm.presentation.screens.login.contract.LoginViewModel
import dev.fsm.ui.components.fields.auth.LoginField
import dev.fsm.ui.components.fields.auth.PasswordField
import dev.fsm.ui.components.states.LoadingScreen
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.buttons
import dev.fsm.ui.theme.spacing
import dev.fsm.utils.ILocalizeExceptionMessage.LocalizeExceptionMessage.localized

@Destination
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LoginScreenContent(
        state = state,
        onIntent = viewModel::handleIntent
    )

    LaunchedEffect(state) {
        if (state is LoginContract.State.Success) navigator.navigate(OrdersScreenDestination.route) {
            popUpTo(LoginScreenDestination.route) {
                inclusive = true
            }
        }
    }
}

@Composable
private fun LoginScreenContent(
    state: LoginContract.State,
    onIntent: (LoginContract.Intent) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // Fields
        var login by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue())
        }
        var password by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue())
        }

        // Checking fields
        val isPasswordLengthCorrect = password.text.isNotEmpty() and (password.text.length == 4)
        val isFieldsNotEmpty = login.text.isNotEmpty() and password.text.isNotEmpty()
        val isLoginEmpty = password.text.isNotEmpty() and login.text.isEmpty()
        val isPasswordNotEmpty = password.text.isNotEmpty()

        val currentWindow = (LocalView.current.context as? Activity)?.window
            ?: throw Exception("Not in an activity - unable to get Window reference")
        WindowCompat.setDecorFitsSystemWindows(currentWindow, false)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .padding(MaterialTheme.spacing.medium),
            verticalArrangement = Arrangement.Center
        ) {
            val passwordFocusRequester = FocusRequester()
            val focusManager = LocalFocusManager.current

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.short_app_name),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(if (state is LoginContract.State.Failure) 43.dp else 78.dp))
            if (state is LoginContract.State.Failure) {
                Text(
                    text = state.exception.localized(),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.size(12.dp))
            }
            LoginField(
                value = login,
                onValueChange = { login = it },
                modifier = Modifier.fillMaxWidth(),
                isError = isLoginEmpty,
                errorMessage = stringResource(id = R.string.login_length_error),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = {
                        passwordFocusRequester.requestFocus()
                    }
                )
            )
            MaterialTheme.spacing.SpacerSmall()
            PasswordField(
                value = password,
                onValueChange = { password = it },
                isError = !isPasswordLengthCorrect and isPasswordNotEmpty,
                errorMessage = stringResource(id = R.string.password_length_error),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        if (isPasswordLengthCorrect and isFieldsNotEmpty) {
                            onIntent.invoke(LoginContract.Intent.Authenticate(params = AuthenticateParams(
                                login = login.text,
                                password = password.text
                            )))
                        }
                    }
                ),
                focusRequester = passwordFocusRequester
            )
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                title = stringResource(id = R.string.sign_in),
                isLoading = state is LoginContract.State.Loading,
                onIntent = {
                    if (isPasswordLengthCorrect and isFieldsNotEmpty) {
                        onIntent.invoke(LoginContract.Intent.Authenticate(params = AuthenticateParams(
                            login = login.text,
                            password = password.text
                        )))
                    } else {
                        onIntent.invoke(LoginContract.Intent.ShowError(exception = FieldsEmpty()))
                    }
                }
            )
        }
    }
}

@Composable
private fun Button(
    title: String,
    isLoading: Boolean,
    onIntent: () -> Unit
) {
    MaterialTheme.buttons.Button(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 56.dp),
        enabled = !isLoading,
        onClick = onIntent
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.W700
        )
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    AppTheme {
        LoginScreenContent(
            state = LoginContract.State.Idle,
            onIntent = {}
        )
    }
}