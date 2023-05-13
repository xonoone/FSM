package dev.fsm.data.utils.jwt

sealed class TokenResponse {
    object Success : TokenResponse()
    data class Failure(val exception: Exception) : TokenResponse()
}
