package dev.fsm.data.network.api.models.order

import com.google.gson.annotations.SerializedName

data class OrderCancel(
    @SerializedName("result") val result: Boolean
)
