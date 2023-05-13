package dev.fsm.presentation.model.report

import android.os.Parcelable
import dev.fsm.presentation.model.report.OrderList.Companion.toOrderListReport
import dev.fsm.utils.Common.toType
import dev.fsm.utils.Mapper
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import dev.fsm.domain.models.report.OrderCompleteField as DomainOrderCompleteField

@Parcelize
data class OrderCompleteField(
    val id: String,
    val type: String,
    val value: @RawValue Any?
) : Parcelable {
    companion object {
        fun OrderCompleteField.toOrderCompleteField(
            toValue: Any?.() -> Any? = Mapper::toValue
        ): DomainOrderCompleteField = DomainOrderCompleteField(
            id = id,
            type = type,
            value = value.toValue()
        )

        fun IField.List.toOrderCompleteField(): DomainOrderCompleteField = DomainOrderCompleteField(
            id = id,
            type = this.toType(),
            value = value?.toOrderListReport()
        )

        fun IField.File.toOrderCompleteField(
            toValue: Any?.() -> Any? = Mapper::toValue
        ): DomainOrderCompleteField = DomainOrderCompleteField(
            id = id,
            type = this.toType(),
            value = value.toValue()
        )

        fun IField.Photo.toOrderCompleteField(
            toValue: Any?.() -> Any? = Mapper::toValue
        ): DomainOrderCompleteField = DomainOrderCompleteField(
            id = id,
            type = this.toType(),
            value = value.toValue()
        )

        fun IField.toOrderCompleteField(): DomainOrderCompleteField = DomainOrderCompleteField(
            id = id,
            type = this.toType(),
            value = value
        )
    }
}
