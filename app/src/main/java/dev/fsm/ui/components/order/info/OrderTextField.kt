package dev.fsm.ui.components.order.info

import androidx.compose.foundation.layout.*
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
fun OrderTextField(
    title: String,
    content: String?
) {
    Category(name = title) {
        Text(
            modifier = Modifier,
            text = content ?: stringResource(id = R.string.no_data),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TextFieldPreview() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OrderTextField(
            title = "Текст",
            content = "Дверь №1"
        )
    }
}