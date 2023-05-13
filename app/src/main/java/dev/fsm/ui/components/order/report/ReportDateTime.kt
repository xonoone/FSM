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
import java.time.LocalTime

@Composable
fun ReportDateTime(
    modifier: Modifier = Modifier,
    title: String,
    required: Boolean = false,
    date: LocalDate? = null,
    onDateChange: (LocalDate) -> Unit,
    time: LocalTime? = null,
    onTimeChange: (LocalTime) -> Unit
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
                value = date,
                onValueChange = onDateChange,
                modifier = Modifier.weight(1F)
            )
            Spacer(modifier = Modifier.size(12.dp))
            MaterialTheme.fields.ClickableTimeField(
                value = time,
                onValueChange = onTimeChange,
                modifier = Modifier.weight(1F)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DateTimePreview() {
    var date: LocalDate? by remember { mutableStateOf(null) }
    var time: LocalTime? by remember { mutableStateOf(null) }

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
                ReportDateTime(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Дата и время",
                    date = date,
                    onDateChange = {
                        date = it
                    },
                    time = time,
                    onTimeChange = {
                        time = it
                    }
                )
                Spacer(modifier = Modifier.size(8.dp))
                ReportDateTime(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Дата и время",
                    date = date,
                    required = true,
                    onDateChange = {
                        date = it
                    },
                    time = time,
                    onTimeChange = {
                        time = it
                    }
                )
            }
        }
    }
}