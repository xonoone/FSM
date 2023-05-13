package dev.fsm.ui.components.states

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import dev.fsm.R
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.corners
import dev.fsm.ui.theme.spacing

@Composable
fun FailureDialog(
    state: MaterialDialogState,
    errorMessage: String?,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    maxHeight: Dp? = (LocalConfiguration.current.screenHeightDp / 3).dp
) {
    MaterialDialog(
        dialogState = state,
        buttons = {
            negativeButton(
                res = android.R.string.cancel,
                onClick = onDismiss,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.secondary
                )
            )
            positiveButton(
                res = R.string.failure_dialog_alert_retry_request,
                onClick = onConfirm,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.secondary
                )
            )
        },
        onCloseRequest = {
            onDismiss.invoke()
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.medium)
                    .heightIn(max = maxHeight ?: Dp.Unspecified)
            ) {
                Text(
                    text = stringResource(id = android.R.string.dialog_alert_title),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
                MaterialTheme.spacing.SpacerMedium()
                Text(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    text = errorMessage ?: stringResource(id = R.string.unexpected_error),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        shape = MaterialTheme.corners.mediumRoundCornerShape(),
        backgroundColor = MaterialTheme.colorScheme.background
    )
}

@Composable
fun FailureDialog(
    state: MaterialDialogState,
    errorMessage: String?,
    onOk: () -> Unit,
    maxHeight: Dp? = (LocalConfiguration.current.screenHeightDp / 3).dp
) {
    MaterialDialog(
        dialogState = state,
        buttons = {
            positiveButton(
                res = android.R.string.ok,
                onClick = onOk,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.secondary
                )
            )
        },
        onCloseRequest = {
            onOk.invoke()
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.medium)
                    .heightIn(max = maxHeight ?: Dp.Unspecified)
            ) {
                Text(
                    text = stringResource(id = android.R.string.dialog_alert_title),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
                MaterialTheme.spacing.SpacerMedium()
                Text(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    text = errorMessage ?: stringResource(id = R.string.unexpected_error),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        shape = MaterialTheme.corners.mediumRoundCornerShape(),
        backgroundColor = MaterialTheme.colorScheme.background
    )
}

@Preview(showBackground = true)
@Composable
private fun FailureScreenPreview() {
    val state = rememberMaterialDialogState()
    val errorMessage = "Some error message."
    AppTheme {
        state.show()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            FailureDialog(
                state = state,
                errorMessage = errorMessage,
                onOk = {}
            )
        }
    }
}