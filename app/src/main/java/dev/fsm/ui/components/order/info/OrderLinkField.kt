package dev.fsm.ui.components.order.info

import androidx.compose.foundation.layout.Column
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
import dev.fsm.ui.components.fields.global.ClickableLink
import dev.fsm.ui.theme.spacing

@Composable
fun OrderLinkField(
    modifier: Modifier = Modifier,
    title: String,
    link: String?
) {
    Category(
        modifier = modifier,
        name = title
    ) {
        link?.let {
            ClickableLink(
                link = link
            )
        } ?: Text(
            text = stringResource(id = R.string.no_data),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LinkFieldPreview() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column {
            OrderLinkField(
                title = "Ссылка",
                link = "https://stackoverflow.com/questions/65567412/jetpack-compose-text-hyperlink-some-section-of-the-text"
            )
            MaterialTheme.spacing.SpacerMedium()
            OrderLinkField(
                title = "Ссылка",
                link = null
            )
        }
    }
}