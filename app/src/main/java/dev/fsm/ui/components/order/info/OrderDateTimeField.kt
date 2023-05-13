package dev.fsm.ui.components.order.info

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.fsm.R
import dev.fsm.ui.components.fields.global.Category
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun OrderDateTimeField(
    modifier: Modifier = Modifier,
    title: String,
    content: LocalDateTime?
) {
    val localDate = content?.toLocalDate()?.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))
    val localTime = content?.toLocalTime()?.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
    Category(
        modifier = modifier,
        name = title
    ) {
        Text(
            text = if (localDate != null && localTime != null) "$localDate  $localTime"
            else stringResource(id = R.string.no_data),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DateTimeFieldPreview() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OrderDateTimeField(
            title = "Дата и время",
            content = LocalDateTime.now()
        )
    }
}