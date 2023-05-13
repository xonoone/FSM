package dev.fsm.data.network.api.models.order

import com.google.gson.annotations.SerializedName
import dev.fsm.domain.models.order.Status

data class OrderStatus(
    @SerializedName("statusCode" ) val code : String? = null,
    @SerializedName("statusName" ) val name : String? = null
) {
    companion object {
        fun OrderStatus.toOrderStatus(): Status = Status(
            code = code ?: "",
            name = name ?: ""
        )
    }
}
