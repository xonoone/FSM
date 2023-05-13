package dev.fsm.ui.components.fields.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import dev.fsm.R
import dev.fsm.ui.theme.*
import dev.fsm.utils.IStringRefactor.StringRefactor.removeCarriageSpaceSymbols

@Composable
fun LoginField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: String? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    focusRequester: FocusRequester? = null,
) {
    MaterialTheme.fields.OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange.invoke(
                it.copy(it.text.removeCarriageSpaceSymbols())
            )
        },
        modifier = modifier.focusRequester(focusRequester ?: FocusRequester()),
        enabled = enabled,
        isError = isError,
        maxLines = 1,
        singleLine = true,
        label = {
            Text(text = stringResource(R.string.login))
        },
        supportingText = {
            errorMessage?.let {
                if (isError)
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Normal
                    )
            }
        },
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions
    )
}

@Preview(showBackground = true)
@Composable
private fun LoginFieldPreview() {
    var login by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var loginError by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.medium)
            ) {
                LoginField(
                    value = login,
                    onValueChange = {
                        login = it
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.size(MaterialTheme.spacing.small))
                LoginField(
                    value = loginError,
                    onValueChange = {
                        loginError = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    isError = true,
                    errorMessage = "Incorrect email"
                )
                Spacer(modifier = Modifier.size(MaterialTheme.spacing.medium))
                MaterialTheme.buttons.Button(onClick = { /*TODO*/ }) {

                }
            }
        }
    }
}