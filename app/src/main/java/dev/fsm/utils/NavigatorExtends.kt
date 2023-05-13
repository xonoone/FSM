package dev.fsm.utils

import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.fsm.destinations.ErrorDialogDestination

object NavigatorExtends {

    fun DestinationsNavigator.showErrorDialog(message: String) {
        navigate(
            direction = ErrorDialogDestination.invoke(
                message = message
            )
        )
    }
}