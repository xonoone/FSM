package dev.fsm.data.network.api.models.order.fields

import com.google.gson.annotations.SerializedName
import dev.fsm.data.utils.Mappers
import dev.fsm.domain.models.order.fields.OrderField as DomainOrderField
import dev.fsm.domain.models.report.ReportField as DomainReportField

data class OrderField(
    @SerializedName("id") val id: String? = null,
    @SerializedName("fieldName") val name: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("owner") val owner: String? = null,
    @SerializedName("value") var value: Any? = null,
    @SerializedName("isVisibleToExecutor") val visible: Boolean? = null,
    @SerializedName("required") val required: Boolean? = null
) {
    companion object {
        fun List<OrderField>.toOrderField(): List<DomainOrderField> = map { it.toOrderField() }
        fun List<OrderField>.toReportField(): List<DomainReportField> =
            map { it.toReportOrderField() }

        fun OrderField.toOrderField(
            toValue: (Any?) -> Any? = Mappers::toValue
        ): DomainOrderField = DomainOrderField(
            id = id ?: throw NullPointerException("ID not found"),
            name = name ?: "",
            type = type ?: "",
            value = toValue.invoke(value),
            visible = visible ?: false,
            required = required ?: false
        )
        fun OrderField.toReportOrderField(
            toValue: (Any?) -> Any? = Mappers::toValue
        ): DomainReportField = DomainReportField(
            id = id                         ?: throw NullPointerException("No id found"),
            name = name                     ?: "",
            type = type                     ?: "",
            value = toValue.invoke(value),
            visible = visible               ?: false,
            required = required             ?: false
        )
    }
}