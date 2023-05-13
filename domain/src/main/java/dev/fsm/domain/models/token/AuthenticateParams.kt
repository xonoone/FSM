package dev.fsm.domain.models.token

data class AuthenticateParams(
    val login: String,
    val password: String
) {
    companion object {
        fun AuthenticateParams.toDomain(): AuthenticationParams = AuthenticationParams(
            login = login,
            password = password
        )
    }
}