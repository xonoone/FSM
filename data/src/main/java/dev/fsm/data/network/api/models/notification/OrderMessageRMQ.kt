package dev.fsm.data.network.api.models.notification

import com.google.gson.annotations.SerializedName

data class OrderMessageRMQ(
    @SerializedName("OrderId"    ) val orderId    : String? = null,
    @SerializedName("OrderName"  ) val orderName  : String? = null,
    @SerializedName("StatusName" ) val statusName : String? = null,
    @SerializedName("Event"      ) val event      : String? = null,
    @SerializedName("Title"      ) val title      : String? = null
)
