package dev.fsm.data.network.api.models.client

import com.google.gson.annotations.SerializedName
import dev.fsm.domain.models.global.client.ClientType as DomainClientType

data class ClientType(
    @SerializedName("typeId"   ) val id   : String? = null,
    @SerializedName("typeName" ) val name : String? = null
) {
    companion object {
        fun ClientType.toClientType(): DomainClientType = DomainClientType(
            name = name     ?: "",
            code = id       ?: ""
        )
    }
}
