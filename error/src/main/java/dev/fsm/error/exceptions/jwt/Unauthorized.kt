package dev.fsm.error.exceptions.jwt

import dev.fsm.error.utils.Codes.Http.ACCESS.UNAUTHORIZED

open class Unauthorized : JWTException {
    constructor() : super(httpCode = UNAUTHORIZED, apiCode = null)

    override val message: String
        get() = "Authentication is required to access the requested resource."
}