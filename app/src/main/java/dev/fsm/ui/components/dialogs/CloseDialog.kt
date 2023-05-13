package dev.fsm.ui.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun CloseDialog(dialogState: MaterialDialogState) {
    MaterialDialog(
        dialogState = dialogState,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.medium),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
            ) {
                Text(
                    text = stringResource(id = R.string.dialog_title_report_close),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.error,
                )
                MaterialTheme.spacing.SpacerSmall()
                Text(
                    text = stringResource(id = R.string.error_message_report_close),
                    style = MaterialTheme.typography.bodyMedium,
                )

            }
        },
        shape = MaterialTheme.corners.mediumRoundCornerShape(),
        elevation = 0.dp,
        buttons = {
            positiveButton(
                res = R.string.ok,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.error
                ),
                disableDismiss = true,
                onClick = { dialogState.hide() }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun CloseDialogPreview() {
    AppTheme {
        val dialogState = rememberMaterialDialogState(true)
        CloseDialog(
            dialogState = dialogState,
        )
    }
}
