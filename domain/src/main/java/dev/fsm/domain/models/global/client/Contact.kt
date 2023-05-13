package dev.fsm.domain.models.global.client

data class Contact(
    val id         : String   = "",
    val clientName : String?  = null,
    val phone      : String?  = null,
    val email      : String?  = null,
    val isMain     : Boolean  = false
)
