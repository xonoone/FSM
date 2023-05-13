package dev.fsm.ui.components.order.report

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.fields
import java.time.LocalDate

@Composable
fun ReportDate(
    modifier: Modifier = Modifier,
    title: String,
    required: Boolean = false,
    value: LocalDate? = null,
    onValueChange: (LocalDate) -> Unit
) {
    Category(
        modifier = modifier,
        name = if (required) "$title*" else title
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MaterialTheme.fields.ClickableDateField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1F)
            )
            Spacer(modifier = Modifier.size(12.dp))
            Spacer(modifier = Modifier.weight(1F))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DatePreview() {
    var date: LocalDate? by remember { mutableStateOf(null) }

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
                ReportDate(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Дата",
                    value = date,
                    onValueChange = {
                        date = it
                    }
                )
                Spacer(modifier = Modifier.size(8.dp))
                ReportDate(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Дата",
                    required = true,
                    value = date,
                    onValueChange = {
                        date = it
                    }
                )
            }
        }
    }
}