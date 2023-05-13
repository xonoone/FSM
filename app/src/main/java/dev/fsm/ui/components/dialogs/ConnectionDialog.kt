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
import dev.fsm.R
import dev.fsm.ui.components.dialogs.styles.DetailDialogStyle
import dev.fsm.ui.components.dialogs.styles.ErrorDialogStyle
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.spacing

@Composable
fun ConnectionDialog(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = MaterialTheme.spacing.small),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onError,
            contentColor = MaterialTheme.colorScheme.error
        )
    ) {
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.medium)
        ) {
            Text(
                text = stringResource(id = R.string.dialog_title_connection_timeout),
                style = MaterialTheme.typography.titleMedium
            )
            MaterialTheme.spacing.SpacerSmall()
            Text(
                modifier = Modifier
                    .sizeIn(maxHeight = 128.dp)
                    .verticalScroll(state = rememberScrollState()),
                text = stringResource(id = R.string.error_message_connection_timeout),
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
                Text(text = stringResource(id = R.string.button_retry))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConnectionDialogPreview() {
    AppTheme {
        ConnectionDialog {

        }
    }
}