package dev.fsm.data.network.api.models

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("errorCode" ) val apiCode: Int? = null,
    @SerializedName("data"      ) val data: T? = null
)