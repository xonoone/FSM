package dev.fsm.ui.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import dev.fsm.R
import dev.fsm.presentation.screens.order.info.contract.OrderContract
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.buttons
import dev.fsm.ui.theme.corners
import dev.fsm.ui.theme.fields
import dev.fsm.ui.theme.spacing
import java.util.UUID

@Composable
fun ReasonDialog(
    state: OrderContract.GetCancelReasons,
    dialogState: MaterialDialogState,
    onConfirm: (index: Int, note: String) -> Unit,
    onCancel: () -> Unit
) {
    when (state) {
        is OrderContract.GetCancelReasons.Failure -> onCancel.invoke()
        is OrderContract.GetCancelReasons.Loading -> Unit
        is OrderContract.GetCancelReasons.Success -> {
            RadioDialog(
                dialogState = dialogState,
                title = stringResource(id = R.string.title_reason_dialog),
                values = state.data.map { it.name },
                onConfirm = { index, note ->
                    dialogState.hide()
                    onConfirm.invoke(index, note)
                },
                onCancel = onCancel
            )
        }
    }
}

@Composable
fun RadioDialog(
    dialogState: MaterialDialogState,
    title: String,
    values: List<String>,
    onConfirm: (index: Int, note: String) -> Unit,
    onCancel: () -> Unit
) {
    var selectedIndex: Int? by rememberSaveable {
        mutableStateOf(null)
    }
    var reason by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
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
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                MaterialTheme.buttons.RadioGroup(
                    modifier = Modifier
                        .weight(1F)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    values = values,
                    selected = selectedIndex,
                    onChange = { newIndex ->
                        selectedIndex = newIndex
                    },
                    content = { item ->
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = item,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                )
                MaterialTheme.fields.OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = reason,
                    onValueChange = {
                        reason = it
                    },
                    label = {
                        Text(text = "Комментарий")
                    }
                )
            }
        },
        shape = MaterialTheme.corners.mediumRoundCornerShape(),
        elevation = 0.dp,
        buttons = {
            negativeButton(
                res = R.string.cancel_dialog,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.secondary
                ),
                onClick = onCancel
            )
            positiveButton(
                res = R.string.ok,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.secondary
                ),
                disableDismiss = true,
                onClick = {
                    if (selectedIndex != null) {
                        onConfirm.invoke(selectedIndex!!, reason.text)
                    }
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun ReasonsDialogPreview() {
    val reasons = mutableListOf<dev.fsm.domain.models.cancel.CancelOrderReason>().apply {
        repeat(20) {
            add(
                element = dev.fsm.domain.models.cancel.CancelOrderReason(
                    id = UUID.randomUUID().toString(),
                    name = "Variant №${it + 1}",
                    note = null
                )
            )
        }
    }
    val dialogState = rememberMaterialDialogState(true)
    val list = reasons.map { it.name }

    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            RadioDialog(
                dialogState = dialogState,
                title = "Укажите причину:",
                values = list,
                onConfirm = { selectedIndex, note ->
                    val selectedReasonId = reasons[selectedIndex].id
                },
                onCancel = { /*TODO*/ }
            )
        }
    }
}