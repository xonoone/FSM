package dev.fsm.ui.components.order.report

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.fields
import dev.fsm.ui.theme.spacing
import dev.fsm.utils.IStringRefactor.StringRefactor.refactorToLong

@Composable
fun ReportLong(
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
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            MaterialTheme.fields.OutlinedTextField(
                modifier = Modifier.weight(1F),
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
            Spacer(modifier = Modifier.size(12.dp))
            Spacer(modifier = Modifier.weight(1F))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LongReportPreview() {
    var long by rememberSaveable(stateSaver = TextFieldValue.Saver) {
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
                ReportLong(
                    value = long,
                    onValueChange = {
                        long = it
                    },
                    title = "Целое число",
                )
                MaterialTheme.spacing.SpacerMedium()
                ReportLong(
                    value = long,
                    onValueChange = {
                        long = it
                    },
                    title = "Целое число",
                    required = true
                )
            }
        }
    }
}