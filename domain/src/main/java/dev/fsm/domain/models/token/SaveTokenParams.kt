package dev.fsm.domain.models.token

data class SaveTokenParams(

    val userId       : String,
    val accessToken  : String,
    val refreshToken : String
)