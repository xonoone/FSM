package dev.fsm.ui.components.order.report

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dev.fsm.R
import dev.fsm.presentation.model.file.FileData
import dev.fsm.ui.components.buttons.AddButton
import dev.fsm.ui.components.permissions.Permission
import dev.fsm.ui.components.permissions.Permissions
import dev.fsm.ui.components.permissions.launchWithPermissions

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ReportPhotos(
    modifier: Modifier = Modifier,
    title: String,
    required: Boolean = false,
    serverValue: List<FileData>,
    localValue: List<Uri>,
    onAdd: (Uri) -> Unit,
    onRemove: (index: Int) -> Unit
) {
    Category(
        modifier = modifier,
        name = if (required) "$title*" else title
    ) {
        val cameraPermissionState = Permission.Photo.permissionsState()
        val cameraDialogState = Permission.Document.dialogState()

        val resultLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetMultipleContents()
        ) { list ->
            if (list.isNotEmpty()) {
                list.forEach {
                    onAdd.invoke(it)
                }
            }
        }
        AddButton(
            name = R.string.button_add_photo,
            onClick = {
                launchWithPermissions(
                    permissionsState = cameraPermissionState,
                    dialogState = cameraDialogState,
                    action = {
                        resultLauncher.launch("image/*")
                    }
                )
            }
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = serverValue,
                key = { it.actualName }
            ) {
                PhotoItem(url = it.url)
            }
            itemsIndexed(
                items = localValue,
                key = { index, _ -> index }
            ) { index, item ->
                val remove = remember { { onRemove.invoke(index) } }
                PhotoItem(
                    content = item,
                    onDelete = remove
                )
            }
        }
        Permissions(
            multiplePermissionsState = cameraPermissionState,
            dialogState = cameraDialogState,
            description = Permission.Photo.description
        )
    }
}