package dev.fsm.domain.models.token

data class Token(

    val userId       : String = "",
    val accessToken  : String = "",
    val refreshToken : String = ""
)