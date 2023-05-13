package dev.fsm.ui.components.order.report

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.fsm.R
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.buttons

@Composable
fun ReportCondition(
    modifier: Modifier = Modifier,
    title: String,
    required: Boolean = false,
    value: Boolean = false,
    onValueChange: (Boolean) -> Unit
) {
    Category(
        modifier = modifier,
        name = if (required) "$title*" else title
    ) {
        MaterialTheme.buttons.Checkbox(
            checked = value,
            onCheckedChange = onValueChange,
            content = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.done),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ConditionPreview() {
    var condition by rememberSaveable { mutableStateOf(false) }
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
                ReportCondition(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Условие",
                    value = condition,
                    onValueChange = {
                        condition = it
                    }
                )
                Spacer(modifier = Modifier.size(8.dp))
                ReportCondition(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Условие",
                    required = true,
                    value = condition,
                    onValueChange = {
                        condition = it
                    }
                )
            }
        }
    }
}