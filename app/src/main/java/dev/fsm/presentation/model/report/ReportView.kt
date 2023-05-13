package dev.fsm.presentation.model.report

import android.os.Parcelable
import dev.fsm.domain.models.report.OrderReport
import dev.fsm.utils.Mapper.toField
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReportView(
    val name: String,
    val description: String,
    val fields: List<IField>
) : Parcelable {
    companion object {
        fun OrderReport.toReportView(): ReportView = ReportView(
            name = name,
            description = description,
            fields = reportFields.mapNotNull { it.toField() }
        )
    }
}
