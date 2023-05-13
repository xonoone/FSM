package dev.fsm.ui.components.permissions

import android.Manifest
import android.os.Build
import androidx.annotation.StringRes
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.vanpra.composematerialdialogs.*
import dev.fsm.R
import dev.fsm.ui.components.states.PermissionDialog

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Permissions(
    multiplePermissionsState: MultiplePermissionsState,
    dialogState: MaterialDialogState,
    @StringRes description: Int
) {
    if (!multiplePermissionsState.allPermissionsGranted) {
        PermissionDialog(
            state = dialogState,
            description = description,
            onConfirm = {
                multiplePermissionsState.launchMultiplePermissionRequest()
            }
        )
    }
}

interface Permission {
    @get:StringRes
    val description: Int

    @Composable
    fun dialogState(): MaterialDialogState


    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun permissionsState(): MultiplePermissionsState

    object Document : Permission {
        override val description: Int
            get() = R.string.document_description

        @Composable
        override fun dialogState(): MaterialDialogState = rememberMaterialDialogState()

        @ExperimentalPermissionsApi
        @Composable
        override fun permissionsState(): MultiplePermissionsState =
            rememberMultiplePermissionsState(
                permissions = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S_V2) listOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) else emptyList()
            )
    }

    object Photo : Permission {
        override val description: Int
            get() = R.string.camera_description

        @Composable
        override fun dialogState(): MaterialDialogState = rememberMaterialDialogState()

        @ExperimentalPermissionsApi
        @Composable
        override fun permissionsState(): MultiplePermissionsState =
            rememberMultiplePermissionsState(
                permissions = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S_V2) listOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) else listOf(Manifest.permission.CAMERA)
            )
    }

    object Location : Permission {
        override val description: Int
            get() = R.string.location_description

        @Composable
        override fun dialogState(): MaterialDialogState = rememberMaterialDialogState()

        @ExperimentalPermissionsApi
        @Composable
        override fun permissionsState(): MultiplePermissionsState =
            rememberMultiplePermissionsState(
                permissions = listOf(Manifest.permission.LOCATION_HARDWARE)
            )
    }

    object Notification : Permission {
        override val description: Int
            get() = R.string.push_description

        @Composable
        override fun dialogState(): MaterialDialogState = rememberMaterialDialogState()

        @ExperimentalPermissionsApi
        @Composable
        override fun permissionsState(): MultiplePermissionsState =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                rememberMultiplePermissionsState(
                    permissions = listOf(Manifest.permission.POST_NOTIFICATIONS)
                )
            } else {
                TODO("VERSION.SDK_INT < TIRAMISU")
            }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
fun launchWithPermissions(
    permissionsState: MultiplePermissionsState,
    dialogState: MaterialDialogState,
    action: () -> Unit
) {
    if (!permissionsState.allPermissionsGranted) {
        if (!dialogState.showing) dialogState.show()
    } else {
        action.invoke()
    }
}

