package dev.fsm.error.exceptions.jwt

import dev.fsm.error.utils.Codes.Api.ACCESS.USER_IS_BLOCKED as API_BLOCKED
import dev.fsm.error.utils.Codes.Http.ACCESS.USER_IS_BLOCKED as HTTP_BLOCKED

class TokenNotWhitelisted : JWTException {
    constructor() : super(httpCode = HTTP_BLOCKED, apiCode = API_BLOCKED)

    override val message: String
        get() = "The user is blocked."
}