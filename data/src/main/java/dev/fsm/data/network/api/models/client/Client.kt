package dev.fsm.data.network.api.models.client

import com.google.gson.annotations.SerializedName
import dev.fsm.data.network.api.models.client.ClientType.Companion.toClientType
import dev.fsm.data.network.api.models.client.Contact.Companion.toContact
import dev.fsm.data.network.api.models.global.AddressData
import dev.fsm.domain.models.global.client.Client as DomainClient
import dev.fsm.domain.models.global.client.Contact as DomainContact

data class Client(
    @SerializedName("id"               ) val id                    : String?             = null,
    @SerializedName("firstName"        ) val firstName             : String?             = null,
    @SerializedName("lastName"         ) val lastName              : String?             = null,
    @SerializedName("middleName"       ) val middleName            : String?             = null,
    @SerializedName("clientPhone"      ) val phone                 : String?             = null,
    @SerializedName("clientMail"       ) val email                 : String?             = null,
    @SerializedName("organizationName" ) val organizationName      : String?             = null,
    @SerializedName("myCompany"        ) val organizationPerformer : String?             = null,
    @SerializedName("clientType"       ) val clientType            : ClientType?         = null,
    @SerializedName("address"          ) val addressData           : AddressData?        = null,
    @SerializedName("contacts"         ) val contacts              : ArrayList<Contact>? = arrayListOf()
) {
    companion object {
        fun Client.toClient(): DomainClient = DomainClient(
            id = id ?: "",
            firstName = firstName,
            lastName = lastName,
            middleName = middleName,
            phone = phone,
            organizationName = organizationName,
            organizationPerformer = organizationPerformer,
            clientType = clientType?.toClientType(),
            contacts = contacts?.toContact() as ArrayList<DomainContact>?
        )
    }
}