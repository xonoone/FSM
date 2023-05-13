package dev.fsm.ui.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.fsm.R
import dev.fsm.ui.components.dialogs.styles.DetailDialogStyle
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.spacing

@Destination(style = DetailDialogStyle::class)
@Composable
fun InfoDialog(
    navigator: DestinationsNavigator,
    route: String,
    title: String
) {
    InfoDialog(
        title = title,
        onClick = {
            navigator.navigate(route) {
                popUpTo(route) {
                    inclusive = true
                }
            }
        }
    )
}

@Composable
private fun InfoDialog(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.medium)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            MaterialTheme.spacing.SpacerBig()
            TextButton(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                onClick = onClick
            ) {
                Text(
                    text = stringResource(id = R.string.ok),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InfoDialogPreview() {
    AppTheme {
        InfoDialog(
            title = "Вы сменили статус заявки",
            onClick = {}
        )
    }
}