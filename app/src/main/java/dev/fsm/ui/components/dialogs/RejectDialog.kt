package dev.fsm.ui.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import dev.fsm.R
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.corners
import dev.fsm.ui.theme.spacing

@Composable
fun RejectDialog(
    dialogState: MaterialDialogState,
    onIntent: () -> Unit
) {
    RejectDialog(
        dialogState = dialogState,
        body = stringResource(id = R.string.dialog_body_reject),
        onIntent = onIntent
    )
}

@Composable
private fun RejectDialog(
    dialogState: MaterialDialogState,
    body: String,
    onIntent: () -> Unit,
) {
    MaterialDialog(
        dialogState = dialogState,
        content = {
            Column(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.medium),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
            ) {
                Text(
                    text = stringResource(id = R.string.dialog_title_order),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.error,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                MaterialTheme.spacing.SpacerSmall()
                Text(
                    modifier = Modifier
                        .sizeIn(maxHeight = 128.dp),
                    text = body,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        shape = MaterialTheme.corners.mediumRoundCornerShape(),
        elevation = MaterialTheme.spacing.small,
        buttons = {
            negativeButton(
                res = R.string.no,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.error
                ),
                onClick = { dialogState.hide() }
            )
            positiveButton(
                res = R.string.yes,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.error
                ),
                onClick = onIntent
            )
        }
    )
}

@Preview
@Composable
private fun RejectDialogPreview() {
    AppTheme {
        RejectDialog(
            dialogState = rememberMaterialDialogState(true),
            body = stringResource(id = R.string.dialog_body_reject),
            onIntent = {}
        )
    }
}