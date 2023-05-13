package dev.fsm.domain.models.global

data class Address(
    val fullAddress   : String?   = null,
    val country       : String?   = null,
    val stateProvince : String?   = null,
    val city          : String?   = null,
    val street        : String?   = null,
    val house         : String?   = null,
    val postalCode    : String?   = null,
    val lat           : Double?   = null,
    val lon           : Double?   = null,
    val apartments    : String?   = null,
    val floor         : String?   = null,
    val entrance      : String?   = null,
    val description   : String?   = null
)