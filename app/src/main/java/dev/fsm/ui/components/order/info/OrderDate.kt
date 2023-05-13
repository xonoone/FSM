package dev.fsm.ui.components.order.info

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import dev.fsm.ui.components.fields.global.Category
import java.time.LocalDateTime
import dev.fsm.R
import dev.fsm.domain.utils.IDateConverter.DateConverter.toDurationString
import dev.fsm.domain.utils.IDateConverter.DateConverter.toLocalDateString

@Composable
fun OrderDate(
    date: LocalDateTime? = null,
    duration: LocalDateTime? = null
) {
    Category(
        name = stringResource(id = R.string.date)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = date?.toLocalDateString() ?: stringResource(id = R.string.no_data),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = date?.toDurationString(duration = duration) ?: "",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.W500
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderDatePreview() {
    OrderDate(
        date = LocalDateTime.of(2022, 11, 16, 10, 0, 0),
        duration = LocalDateTime.of(1970, 1, 1, 1, 0, 0)
    )
}