package dev.fsm.ui.components.dialogs.styles

import androidx.compose.ui.window.DialogProperties
import com.ramcosta.composedestinations.spec.DestinationStyle

object DetailDialogStyle : DestinationStyle.Dialog {
    override val properties: DialogProperties
        get() = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = true,
            decorFitsSystemWindows = true,
            usePlatformDefaultWidth = true
        )
}