package dev.fsm.ui.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.fsm.ui.components.dialogs.styles.ErrorDialogStyle
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.spacing

@Destination(style = ErrorDialogStyle::class)
@Composable
fun ErrorDialog(
    navigator: DestinationsNavigator,
    message: String
) {
    ErrorDialog(
        message = message,
        onClick = { navigator.popBackStack() }
    )
}

@Composable
private fun ErrorDialog(
    modifier: Modifier = Modifier,
    message: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = MaterialTheme.spacing.small),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onError
        )
    ) {
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.medium)
        ) {
            Text(
                text = stringResource(id = android.R.string.dialog_alert_title),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.titleMedium
            )
            MaterialTheme.spacing.SpacerSmall()
            Text(
                modifier = Modifier
                    .sizeIn(maxHeight = 128.dp)
                    .verticalScroll(state = rememberScrollState()),
                text = message,
                style = MaterialTheme.typography.bodyMedium
            )
            MaterialTheme.spacing.SpacerExtraSmall()
            TextButton(
                modifier = Modifier.align(Alignment.End),
                onClick = onClick,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text(text = stringResource(id = android.R.string.ok))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorDialogPreview() {
    ErrorDialog(
        modifier = Modifier.padding(16.dp),
        message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam non varius tellus. Nunc nec nisl arcuLorem ipsum dolor sit amet, consectetur adipiscing elit. Nam non varius tellus. Nunc nec nisl arcuLorem ipsum dolor sit amet, consectetur adipiscing elit. Nam non varius tellus. Nunc nec nisl arcu",
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun ErrorDialogPreviewLight() {
    AppTheme(darkTheme = false) {
        ErrorDialogPreview()
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorDialogPreviewDark() {
    AppTheme(darkTheme = true) {
        ErrorDialogPreview()
    }
}