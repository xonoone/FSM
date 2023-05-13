package dev.fsm.ui.components.fields.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import dev.fsm.R
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.buttons
import dev.fsm.ui.theme.fields
import dev.fsm.ui.theme.spacing
import dev.fsm.utils.IStringRefactor.StringRefactor.refactorToNumPass

@Composable
fun PasswordField(
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
    var passwordVisibility by remember { mutableStateOf(false) }
    val maxChar = 4

    MaterialTheme.fields.OutlinedTextField(
        value = value,
        onValueChange = {
            if (it.text.length <= maxChar) {
                onValueChange.invoke(
                    it.copy(it.text.refactorToNumPass())
                )
            }
        },
        modifier = modifier.focusRequester(focusRequester ?: FocusRequester()),
        enabled = enabled,
        isError = isError,
        label = {
            Text(text = stringResource(R.string.password))
        },
        maxLines = 1,
        singleLine = true,
        supportingText = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                errorMessage?.let {
                    if (isError)
                        Text(
                            modifier = Modifier.weight(1F),
                            text = it,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Normal
                        )
                    Spacer(modifier = Modifier.size(MaterialTheme.spacing.small))
                }
                Text(
                    text = "${value.text.length}/$maxChar",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        },
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions.copy(keyboardType = KeyboardType.NumberPassword),
        visualTransformation = if (passwordVisibility) VisualTransformation.None
        else PasswordVisualTransformation(),
        trailingIcon = {
            val iconDescription =
                if (passwordVisibility) stringResource(id = R.string.icon_hide_pass_description)
                else stringResource(id = R.string.icon_show_pass_description)
            val iconVector = if (passwordVisibility) Icons.Outlined.VisibilityOff
            else Icons.Outlined.Visibility
            IconButton(
                onClick = { passwordVisibility = !passwordVisibility }
            ) {
                Icon(
                    imageVector = iconVector,
                    contentDescription = iconDescription
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PasswordFieldPreview() {
    var password by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var passwordError by rememberSaveable(stateSaver = TextFieldValue.Saver) {
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
                PasswordField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.size(MaterialTheme.spacing.small))
                PasswordField(
                    value = passwordError,
                    onValueChange = {
                        passwordError = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    isError = true,
                    errorMessage = "Incorrect password length"
                )
                Spacer(modifier = Modifier.size(MaterialTheme.spacing.medium))
                MaterialTheme.buttons.Button(onClick = { /*TODO*/ }) {

                }
            }
        }
    }
}