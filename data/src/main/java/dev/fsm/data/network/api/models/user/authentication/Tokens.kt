package dev.fsm.data.network.api.models.user.authentication

import com.google.gson.annotations.SerializedName

data class Tokens (
    @SerializedName("id"           ) val userId       : String? = null,
    @SerializedName("accessToken"  ) val accessToken  : String? = null,
    @SerializedName("refreshToken" ) val refreshToken : String? = null
)