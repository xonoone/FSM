package dev.fsm.data.storage.models.params.tokens

import dev.fsm.domain.models.token.AuthenticationParams

data class AuthTokenParams(
    val login       :   String,
    val password    :   String
) {
    companion object {
        fun AuthenticationParams.toAuthTokenParams(): AuthTokenParams = AuthTokenParams(
            login = login,
            password = password
        )
    }
}