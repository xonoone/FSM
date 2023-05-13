package dev.fsm.presentation.screens.order.report.contract

import android.content.Context
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.fsm.core.BaseViewModel
import dev.fsm.destinations.OrdersScreenDestination
import dev.fsm.domain.models.files.FileData
import dev.fsm.domain.models.files.UploadFilesParams
import dev.fsm.domain.models.report.OrderCompleteReportParams
import dev.fsm.domain.models.report.OrderReportParams
import dev.fsm.domain.usecase.files.UploadFiles
import dev.fsm.domain.usecase.report.CompleteOrderWithReportById
import dev.fsm.domain.utils.INullabilityChecker.NullabilityChecker.isNull
import dev.fsm.domain.utils.INullabilityChecker.NullabilityChecker.notNull
import dev.fsm.domain.utils.Response
import dev.fsm.error.exceptions.ui.FieldsEmpty
import dev.fsm.presentation.model.file.FileData.Companion.toFileData
import dev.fsm.presentation.model.report.IField
import dev.fsm.presentation.model.report.OrderCompleteField
import dev.fsm.presentation.model.report.OrderCompleteField.Companion.toOrderCompleteField
import dev.fsm.presentation.model.report.ReportView.Companion.toReportView
import dev.fsm.utils.Common.toTempFile
import dev.fsm.utils.Common.toType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val order: CompleteOrderWithReportById,
    private val files: UploadFiles,
    @ApplicationContext private val context: Context,
) : BaseViewModel<ReportContract.State, ReportContract.Intent, ReportContract.Effect>() {
    override fun createInitialState(): ReportContract.State = ReportContract.State(
        report = ReportContract.ReportState.Idle,
        complete = ReportContract.CompleteState.Idle
    )

    override fun handleIntent(event: ReportContract.Intent) {
        when (event) {
            is ReportContract.Intent.CompleteOrder -> completeOrder(
                id = event.id,
                fields = event.fields
            )
            is ReportContract.Intent.GetReport -> getReportInfo(id = event.id)
            is ReportContract.Intent.ShowErrorDialog -> setEffect { ReportContract.Effect.ShowErrorDialog(exception = event.exception) }
        }
    }

    private fun getReportInfo(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            setState { copy(report = ReportContract.ReportState.Loading) }
            when (val response = order.get(params = OrderReportParams(orderId = id))) {
                is Response.Error -> setEffect {
                    ReportContract.Effect.ShowErrorDialog(exception = response.exception)
                }

                is Response.Success -> {
                    val data = response.data
                    if (data != null) setState {
                        copy(report = ReportContract.ReportState.Loaded(data = data.toReportView()))
                    }
                    else setEffect {
                        ReportContract.Effect.ShowErrorDialog(NullPointerException())
                    }
                }
            }
        }
    }

    private fun completeOrder(id: String, fields: List<IField>) {
        viewModelScope.launch(Dispatchers.IO) {
            fields.forEach { field ->
                if (field.required) {
                    when (field) {
                        is IField.File -> {
                            if (field.localValue.isEmpty() or field.localValue.isNull()) {
                                setEffect { ReportContract.Effect.ShowErrorDialog(exception = FieldsEmpty()) }
                                return@launch
                            }
                        }

                        is IField.Photo -> {
                            if (field.localValue.isEmpty() or field.localValue.isNull()) {
                                setEffect { ReportContract.Effect.ShowErrorDialog(exception = FieldsEmpty()) }
                                return@launch
                            }
                        }

                        is IField.Condition -> {
                            if (field.value == false or field.value.isNull()) {
                                setEffect { ReportContract.Effect.ShowErrorDialog(exception = FieldsEmpty()) }
                                return@launch
                            }
                        }

                        is IField.List -> {
                            var count = 0
                            field.value?.values?.forEach {
                                if (!it.isChecked) {
                                    count++
                                }
                            }
                            if (field.value?.values?.size == count) {
                                setEffect { ReportContract.Effect.ShowErrorDialog(exception = FieldsEmpty()) }
                                return@launch
                            }
                        }

                        is IField.Date, is IField.DateTime -> {
                            if (field.value.isNull()) {
                                setEffect { ReportContract.Effect.ShowErrorDialog(exception = FieldsEmpty()) }
                                return@launch
                            }
                        }

                        else -> {
                            if (field.value.isNull() or field.value.toString().isEmpty()) {
                                setEffect { ReportContract.Effect.ShowErrorDialog(exception = FieldsEmpty()) }
                                return@launch
                            }
                        }
                    }
                }
            }
            setState { copy(complete = ReportContract.CompleteState.Loading) }

            val domainFields = fields.map {
                when (it) {
                    is IField.Photo -> {
                        if (it.localValue.isNotEmpty() and it.localValue.notNull()) it.toFieldFiles()
                        else it.toOrderCompleteField()
                    }

                    is IField.File -> {
                        if (it.localValue.isNotEmpty() and it.localValue.notNull()) it.toFieldFiles()
                        else it.toOrderCompleteField()
                    }

                    is IField.List -> it.toOrderCompleteField()
                    else -> it.toOrderCompleteField()
                }
            }

            val response = order.complete(
                params = OrderCompleteReportParams(
                    orderId = id,
                    reportFields = domainFields
                )
            )
            when (response) {
                is Response.Error -> {
                    setState {
                        copy(complete = ReportContract.CompleteState.Failure(exception = response.exception))
                    }
                }

                is Response.Success -> setEffect {
                    ReportContract.Effect.ShowToast(message = "Вы завершили заявку")
                }
            }
        }
    }

    private suspend fun IField.toFieldFiles(): dev.fsm.domain.models.report.OrderCompleteField {
        if (this is IField.File) {
            when (val response = uploadFiles()) {
                is Response.Error -> setEffect {
                    ReportContract.Effect.ShowErrorDialog(exception = response.exception)
                }

                is Response.Success -> {
                    val data = response.data
                    if (data != null) {
                        return data.toOrderCompleteField()
                    } else {
                        setEffect {
                            ReportContract.Effect.ShowErrorDialog(exception = NullPointerException())
                        }
                    }
                }
            }
        }
        if (this is IField.Photo) {
            when (val response = uploadFiles()) {
                is Response.Error -> setEffect {
                    ReportContract.Effect.ShowErrorDialog(exception = response.exception)
                }

                is Response.Success -> {
                    val data = response.data
                    if (data != null) {
                        return data.toOrderCompleteField()
                    } else {
                        setEffect {
                            ReportContract.Effect.ShowErrorDialog(exception = NullPointerException())
                        }
                    }
                }
            }
        }
        return dev.fsm.domain.models.report.OrderCompleteField(
            id = id,
            type = toType(),
            value = value
        )
    }

    private suspend fun IField.Photo.uploadFiles(): Response<OrderCompleteField> {
        val fileList = localValue.mapNotNull { it.toTempFile(context = context) }
        val response = files.upload(params = UploadFilesParams(files = fileList))
        return when (response) {
            is Response.Error -> Response.Error(exception = response.exception)
            is Response.Success -> {
                val arr = arrayListOf<FileData>()
                value?.forEach {
                    arr.add(it.toFileData())
                }
                response.data?.forEach {
                    arr.add(it)
                }
                Response.Success(
                    data = OrderCompleteField(
                        id = id,
                        type = this.toType(),
                        value = arr
                    )
                )
            }
        }
    }

    private suspend fun IField.File.uploadFiles(): Response<OrderCompleteField> {
        val fileList = localValue.mapNotNull { it.toTempFile(context = context) }
        val response = files.upload(params = UploadFilesParams(files = fileList))
        return when (response) {
            is Response.Error -> Response.Error(exception = response.exception)
            is Response.Success -> Response.Success(
                data = OrderCompleteField(
                    id = id,
                    type = this.toType(),
                    value = response.data
                )
            )
        }
    }
}