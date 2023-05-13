package dev.fsm.presentation.screens.order.report.contract

import com.ramcosta.composedestinations.spec.Direction
import dev.fsm.core.ViewEffect
import dev.fsm.core.ViewIntent
import dev.fsm.core.ViewState
import dev.fsm.presentation.model.report.IField
import dev.fsm.presentation.model.report.ReportView

object ReportContract {

    data class State(
        val report: ReportState,
        val complete: CompleteState
    ) : ViewState

    sealed class CompleteState {
        object Idle : CompleteState()
        object Loading : CompleteState()
        object Success: CompleteState()
        data class Failure(val exception: Exception): CompleteState()
    }
    sealed class ReportState {
        object Idle : ReportState()
        object Loading : ReportState()
        data class Loaded(val data: ReportView): ReportState()
        data class Failure(val exception: Exception): ReportState()
    }

    sealed class Effect : ViewEffect {
        data class ShowToast(val message: String) : Effect()
        data class ShowErrorDialog(val exception: Exception) : Effect()
        data class ShowSuccessDialog(val message: String) : Effect()
        data class ChangeStateButton(val value: Boolean) : Effect()
        data class NavigateWithPopUp(val direction: Direction) : Effect()
    }

    sealed class Intent : ViewIntent {
        class GetReport(val id: String) : Intent()
        class CompleteOrder(val id: String, val fields: List<IField>) : Intent()

        data class ShowErrorDialog(val exception: Exception) : Intent()
    }
}