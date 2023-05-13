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

@Composable
fun OrderMoneyField(
    modifier: Modifier = Modifier,
    title: String,
    content: Double?
) {
    Category(
        modifier = modifier,
        name = title
    ) {
        Text(
            text = if (content != null) "$content ₽" else stringResource(id = R.string.no_data),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MoneyFieldPreview() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OrderMoneyField(
            title = "Деньги",
            content = 25000.0
        )
    }
}