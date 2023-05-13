package dev.fsm.ui.components.states

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import dev.fsm.ui.theme.spacing

@Composable
fun PermissionDialog(
    state: MaterialDialogState,
    @StringRes description: Int,
    onConfirm: () -> Unit,
    maxHeight: Dp? = (LocalConfiguration.current.screenHeightDp / 3).dp
) {
    MaterialDialog(
        dialogState = state,
        content = {
            Column(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.medium)
                    .heightIn(max = maxHeight ?: Dp.Unspecified)
            ) {
                Text(
                    text = stringResource(id = R.string.permission_title),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                MaterialTheme.spacing.SpacerMedium()
                Text(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    text = stringResource(id = description),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        shape = RoundedCornerShape(10.dp),
        elevation = 0.dp,
        buttons = {
            negativeButton(
                text = stringResource(id = R.string.cancel),
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.secondary
                )
            )
            positiveButton(
                text = stringResource(id = R.string.accept),
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.secondary
                ),
                onClick = onConfirm
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PermissionDialogPreview() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val dialogState = rememberMaterialDialogState()
            if (!dialogState.showing) dialogState.show()
            PermissionDialog(
                state = dialogState,
                description = R.string.document_description,
                onConfirm = { /*TODO*/ })
        }
    }
}