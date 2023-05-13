package dev.fsm.presentation.model.report

import android.os.Parcelable
import dev.fsm.utils.Mapper
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import dev.fsm.domain.models.report.ReportField as DomainReportField

@Parcelize
data class ReportField(
    val id       : String,
    val name     : String,
    val type     : String,
    var value    : @RawValue Any?   =   null,
    val visible  : Boolean          =   false,
    val required : Boolean          =   false
): Parcelable {
    companion object {
        fun ReportField.toReportField(
            toValue: Any?.() -> Any? = Mapper::toValue
        ): DomainReportField = DomainReportField(
            id = id,
            name = name,
            type = type,
            value = value.toValue(),
            visible = visible,
            required = required
        )
    }
}