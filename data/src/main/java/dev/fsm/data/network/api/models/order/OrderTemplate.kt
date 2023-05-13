package dev.fsm.data.network.api.models.order

import com.google.gson.annotations.SerializedName
import dev.fsm.data.network.api.models.order.fields.OrderField

data class OrderTemplate(
    @SerializedName("id"                ) val id              : String?                = null,
    @SerializedName("orderTemplateName" ) val name            : String?                = null,
    @SerializedName("orderType"         ) val type            : String?                = null,
    @SerializedName("duration"          ) val duration        : String?                = null,
    @SerializedName("orderDescription"  ) val description     : String?                = null,
    @SerializedName("skills"            ) val necessarySkills : ArrayList<String>?     = arrayListOf(),
    @SerializedName("fields"            ) val orderFields     : ArrayList<OrderField>? = arrayListOf(),
    @SerializedName("executorFields"    ) var reportFields    : ArrayList<OrderField>? = arrayListOf()
)