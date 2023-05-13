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
fun OrderDescription(
    modifier: Modifier = Modifier,
    description: String
) {
    Category(
        modifier = modifier,
        name = stringResource(id = R.string.description)
    ) {
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderDescriptionPreview() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OrderDescription(
            description = "Здесь находится описание задачи для монтажника."
        )
    }
}