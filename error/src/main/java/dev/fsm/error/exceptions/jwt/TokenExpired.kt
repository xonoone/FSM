package dev.fsm.error.exceptions.jwt

import dev.fsm.error.utils.Codes.Http.ACCESS.TOKEN_EXPIRED as API_EXPIRED
import dev.fsm.error.utils.Codes.Api.ACCESS.TOKEN_EXPIRED as HTTP_EXPIRED

open class TokenExpired : JWTException {
    constructor() : super(httpCode = HTTP_EXPIRED, apiCode = API_EXPIRED)

    override val message: String
        get() = "The provided token has expired."
}