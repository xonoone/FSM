package dev.fsm.domain.models.global.client

data class Client(
    val id                    : String              = "",
    val firstName             : String?             = null,
    val lastName              : String?             = null,
    val middleName            : String?             = null,
    val phone                 : String?             = null,
    val organizationName      : String?             = null,
    val organizationPerformer : String?             = null,
    val clientType            : ClientType?         = null,
    val contacts              : ArrayList<Contact>? = arrayListOf()
)