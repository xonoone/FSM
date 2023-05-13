package dev.fsm.domain.models.token

data class AuthenticationParams(
    val login    : String,
    val password : String
)