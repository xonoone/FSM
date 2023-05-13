package dev.fsm.data.database.entities

import androidx.room.ColumnInfo

data class Client(
    @ColumnInfo("id"               ) val id                    : String?             = null,
    @ColumnInfo("first_name"        ) val firstName             : String?             = null,
    @ColumnInfo("last_name"         ) val lastName              : String?             = null,
    @ColumnInfo("middle_name"       ) val middleName            : String?             = null,
    @ColumnInfo("client_phone"      ) val phone                 : String?             = null,
    @ColumnInfo("client_mail"       ) val email                 : String?             = null,
    @ColumnInfo("organization_name" ) val organizationName      : String?             = null,
    @ColumnInfo("my_company"        ) val organizationPerformer : String?             = null,
    @ColumnInfo("client_type"       ) val clientType            : ClientType?         = null,
    @ColumnInfo("address"          ) val addressData           : AddressData?        = null,
    @ColumnInfo("contacts"         ) val contacts              : ArrayList<Contact>? = arrayListOf()
)
