package dev.fsm.data.network.api.models.client

import com.google.gson.annotations.SerializedName
import dev.fsm.domain.models.global.client.Contact as DomainContact
data class Contact(
    @SerializedName("id"                ) val id         : String?  = null,
    @SerializedName("clientContactName" ) val clientName : String?  = null,
    @SerializedName("phone"             ) val phone      : String?  = null,
    @SerializedName("email"             ) val email      : String?  = null,
    @SerializedName("isMain"            ) val isMain     : Boolean? = null
) {
    companion object {
        fun Collection<Contact>.toContact(): Collection<DomainContact> = map { it.toContact() }
        fun Contact.toContact(): DomainContact = DomainContact(
            id = id                     ?: "",
            clientName = clientName,
            phone = phone,
            email = email,
            isMain = isMain             ?: false
        )
    }
}
