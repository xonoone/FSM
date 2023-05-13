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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.fields

@Composable
fun ReportText(
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
        Row {
            MaterialTheme.fields.OutlinedTextField(
                modifier = Modifier.weight(1F),
                value = value,
                onValueChange = { newText ->
                    onValueChange.invoke(newText)
                },
                keyboardActions = keyboardActions,
                keyboardOptions = keyboardOptions.copy(keyboardType = KeyboardType.Text)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReportTextPreview() {
    var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
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
                    .padding(8.dp)
            ) {
                ReportText(
                    value = text,
                    onValueChange = {
                        text = it
                    },
                    title = "Комментарий/текст",
                    required = true
                )
            }
        }
    }
}

@Composable
fun Category(
    modifier: Modifier = Modifier,
    name: String,
    nameStyle: TextStyle = MaterialTheme.typography.titleMedium,
    space: Dp = 4.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = name,
            style = nameStyle
        )
        Spacer(modifier = Modifier.size(space))
        content()
    }
}