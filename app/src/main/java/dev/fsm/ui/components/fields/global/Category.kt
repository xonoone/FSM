package dev.fsm.ui.components.fields.global

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import dev.fsm.ui.theme.spacing

@Composable
fun Category(
    modifier: Modifier = Modifier,
    name: String,
    nameStyle: TextStyle = MaterialTheme.typography.titleMedium,
    space: Dp = MaterialTheme.spacing.extraSmall,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = name,
            style = nameStyle
        )
        Spacer(modifier = Modifier.size(space))
        content()
    }
}