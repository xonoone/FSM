package dev.fsm.ui.components.order.report

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dev.fsm.R
import dev.fsm.presentation.model.file.FileData
import dev.fsm.ui.components.buttons.AddButton
import dev.fsm.ui.components.permissions.Permission
import dev.fsm.ui.components.permissions.Permissions
import dev.fsm.ui.components.permissions.launchWithPermissions
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.spacing
import dev.fsm.utils.Common.getName

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ReportFiles(
    modifier: Modifier = Modifier,
    title: String,
    required: Boolean = false,
    serverValue: List<FileData>,
    localValue: List<Uri>,
    onAdd: (Uri) -> Unit,
    onRemove: (index: Int) -> Unit
) {
    val context = LocalContext.current
    Category(
        modifier = modifier,
        name = if (required) "$title*" else title
    ) {
        val docPermissionState = Permission.Document.permissionsState()
        val docDialogState = Permission.Document.dialogState()

        val pickLauncherDoc = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetMultipleContents()
        ) { list ->
            if (list.isNotEmpty()) {
                list.forEach {
                    onAdd.invoke(it)
                }
            }
        }
        AddButton(
            name = R.string.button_add_doc,
            onClick = {
                launchWithPermissions(
                    permissionsState = docPermissionState,
                    dialogState = docDialogState,
                    action = {
                        pickLauncherDoc.launch("application/*")
                    }
                )
            }
        )
        Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)) {
            serverValue.forEach { item ->
                FileItem(
                    name = item.initialName ?: item.actualName,
                    url = item.url
                )
            }
            localValue.forEachIndexed { index, item ->
                val remove = remember { { onRemove.invoke(index) } }
                FileItem(
                    name = item.getName(context = context),
                    uri = item,
                    onDelete = remove
                )
            }
        }
        Permissions(
            multiplePermissionsState = docPermissionState,
            dialogState = docDialogState,
            description = Permission.Document.description
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ReportFilesButtonPreview() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.spacing.medium)
            ) {
                AddButton(
                    name = R.string.button_add_doc,
                    onClick = {}
                )
            }
        }
    }
}