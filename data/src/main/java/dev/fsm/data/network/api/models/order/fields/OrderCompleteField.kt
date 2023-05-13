package dev.fsm.data.network.api.models.order.fields

import com.google.gson.annotations.SerializedName
import dev.fsm.data.utils.Mappers
import dev.fsm.domain.models.report.OrderCompleteField as DomainOrderCompleteField

data class OrderCompleteField(
    @SerializedName("id"     ) val id       :   String,
    @SerializedName("type"   ) val type     :   String,
    @SerializedName("value" )  val value    :   Any?
) {
    companion object {
        fun DomainOrderCompleteField.toOrderCompleteField(
            toValue: Any?.() -> Any? = Mappers::toValue
        ): OrderCompleteField = OrderCompleteField(
            id = id,
            type = type,
            value = value.toValue()
        )
    }
}