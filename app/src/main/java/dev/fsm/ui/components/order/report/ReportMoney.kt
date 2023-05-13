package dev.fsm.ui.components.order.report

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.fsm.R
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.fields
import dev.fsm.ui.theme.spacing
import dev.fsm.utils.IStringRefactor.StringRefactor.refactorToLong

@Composable
fun ReportMoney(
    modifier: Modifier = Modifier,
    title: String,
    required: Boolean = false,
    value: TextFieldValue = TextFieldValue(),
    onValueChange: (TextFieldValue) -> Unit,
    focusManager: FocusManager = LocalFocusManager.current,
    keyboardActions: KeyboardActions = KeyboardActions(
        onDone = { focusManager.clearFocus() }
    ),
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Done
    ),
) {
    Category(
        modifier = modifier,
        name = if (required) "$title*" else title
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(
                modifier = Modifier.weight(1F),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MaterialTheme.fields.OutlinedTextField(
                    modifier = Modifier.weight(0.5F),
                    value = value,
                    onValueChange = { newText ->
                        onValueChange.invoke(
                            newText.copy(newText.text.refactorToLong())
                        )
                    },
                    singleLine = true,
                    keyboardActions = keyboardActions,
                    keyboardOptions = keyboardOptions.copy(keyboardType = KeyboardType.Decimal)
                )
                MaterialTheme.spacing.SpacerSmall()
                Text(
                    text = stringResource(id = R.string.rubles),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.size(12.dp))
            Spacer(modifier = Modifier.weight(1F))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MoneyPreview() {
    var money by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.spacing.medium)
            ) {
                ReportMoney(
                    value = money,
                    onValueChange = {
                        money = it
                    },
                    title = "Деньги",
                )
                MaterialTheme.spacing.SpacerMedium()
                ReportMoney(
                    value = money,
                    onValueChange = {
                        money = it
                    },
                    title = "Деньги",
                    required = true
                )
            }
        }
    }
}