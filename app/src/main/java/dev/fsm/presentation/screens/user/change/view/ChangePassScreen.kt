package dev.fsm.presentation.screens.user.change.view

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.fsm.R
import dev.fsm.destinations.ErrorDialogDestination
import dev.fsm.presentation.screens.user.change.contract.ChangePassContract
import dev.fsm.presentation.screens.user.change.contract.ChangePassViewModel
import dev.fsm.presentation.screens.user.change.models.ChangePassParams
import dev.fsm.ui.components.app_bars.PageAppBar
import dev.fsm.ui.components.fields.auth.PasswordField
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.buttons
import dev.fsm.ui.theme.spacing
import dev.fsm.utils.ILocalizeExceptionMessage.LocalizeExceptionMessage.localized

@Destination
@Composable
fun ChangePassScreen(
    viewModel: ChangePassViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            PageAppBar(
                title = stringResource(id = R.string.app_bar_change_pass),
                onBackPressed = { if (state !is ChangePassContract.State.Loading)
                    navigator.navigateUp()
                }
            )
        },
        content = { innerPadding ->
            ChangePassScreenContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                isLoading = state is ChangePassContract.State.Loading,
                onIntent = viewModel::handleIntent
            )
        }
    )

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect { event ->
            when (event) {
                is ChangePassContract.Effect.ShowErrorDialog -> navigator.navigate(
                    ErrorDialogDestination.invoke(message = event.exception.localized(context))
                )

                is ChangePassContract.Effect.ShowToast ->
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
private fun ChangePassScreenContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    onIntent: (ChangePassContract.Intent) -> Unit
) {
    var password by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var confirmPassword by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    // Checking fields
    val isPasswordLengthCorrect = password.text.isNotEmpty() && (password.text.length != 4)
    val isConfirmPasswordLengthCorrect = confirmPassword.text.isNotEmpty() && (confirmPassword.text.length != 4)

    val isPasswordsMismatch: Boolean = password.text != confirmPassword.text
    val isPasswordsNotEmpty: Boolean = password.text.isNotEmpty() && confirmPassword.text.isNotEmpty()

    val errorMessageMismatch: String = stringResource(id = R.string.password_missmatch)
    val errorMessageLength: String = stringResource(id = R.string.password_length_error)

    val passwordFocusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current
    Surface(modifier = modifier) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.create_password),
                style = MaterialTheme.typography.bodyMedium
            )
            MaterialTheme.spacing.SpacerExtraSmall()
            PasswordField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = { password = it },
                isError = isPasswordsNotEmpty && isPasswordLengthCorrect,
                errorMessage = errorMessageLength,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { passwordFocusRequester.requestFocus() })
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.confirm_password),
                style = MaterialTheme.typography.bodyMedium
            )
            MaterialTheme.spacing.SpacerExtraSmall()
            PasswordField(
                modifier = Modifier.fillMaxWidth(),
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                isError = isPasswordsNotEmpty && (isConfirmPasswordLengthCorrect or isPasswordsMismatch),
                errorMessage = if (isConfirmPasswordLengthCorrect) errorMessageLength else errorMessageMismatch,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                        onIntent.invoke(ChangePassContract.Intent.ChangePassword(
                            params = ChangePassParams(newPassword = confirmPassword.text)
                        ))
                    }
                ),
                focusRequester = passwordFocusRequester
            )
            if (
                isPasswordsNotEmpty
                and !isPasswordsMismatch
                and !isPasswordLengthCorrect
                and !isConfirmPasswordLengthCorrect
            ) {
                MaterialTheme.spacing.SpacerMedium()
                MaterialTheme.buttons.Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 56.dp),
                    enabled = !isLoading,
                    onClick = {
                        onIntent.invoke(ChangePassContract.Intent.ChangePassword(
                            params = ChangePassParams(newPassword = password.text)
                        ))
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.change),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.W700
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ChangePassScreenPreview() {
    AppTheme {
        ChangePassScreenContent(
            isLoading = false,
            onIntent = {}
        )
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//private fun ConfirmPassword(
//    /*TODO: event: (ConfirmEvent) -> Unit*/
//    onClick: () -> Unit
//) {
//    var fieldValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
//        mutableStateOf(TextFieldValue())
//    }
//
//    Column(modifier = Modifier.fillMaxSize()) {
//        Text(
//            modifier = Modifier.fillMaxWidth(),
//            text = stringResource(id = R.string.confirm_password),
//            style = Typography.bodyMedium
//        )
//        Spacer(modifier = Modifier.height(4.dp))
//        OutlinedTextField(
//            value = fieldValue,
//            onValueChange = { value ->
//                fieldValue = value.copy(text = value.text)
//            },
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(10.dp),
//            colors = OutlinedTextFieldColors(),
//
//            )
//        Spacer(modifier = Modifier.height(16.dp))
//        PositiveButton(
//            modifier = Modifier.fillMaxWidth(),
//            enabled = fieldValue.text.isNotEmpty(),
//            text = R.string.confirm
//        ) {
//            onClick.invoke()
//        }
//        Spacer(modifier = Modifier.height(8.dp))
//        TextButton(
//            modifier = Modifier.fillMaxWidth(),
//            onClick = {}
//        ) {
//            Text(
//                text = stringResource(id = R.string.send_code_again),
//                style = TextStyle(
//                    fontFamily = Roboto,
//                    fontStyle = FontStyle.Normal,
//                    fontWeight = FontWeight.W500,
//                    fontSize = 20.sp,
//                    color = BlueDarkColor
//                )
//            )
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun ConfirmPasswordPreview() {
//    ConfirmPassword {}
//}
//
//@Composable
//private fun PasswordError(
//    @StringRes errorMessage: Int
//) {
//    Text(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(32.dp),
//        text = stringResource(id = errorMessage),
//        textAlign = TextAlign.Center,
//        fontWeight = FontWeight.W500,
//        color = GrayDarkColor,
//        fontSize = 20.sp,
//        fontFamily = Roboto
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun PasswordErrorPreview() {
//    PasswordError(R.string.default_error_message)
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ScreenChangePassPreview() {
//    ChangePassScreen(action = {})
//}