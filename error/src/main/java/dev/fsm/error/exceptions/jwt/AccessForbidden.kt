package dev.fsm.error.exceptions.jwt

import dev.fsm.error.utils.Codes.Http.ACCESS.ACCESS_FORBIDDEN

open class AccessForbidden : JWTException {
    constructor() : super(httpCode = ACCESS_FORBIDDEN, apiCode = null) // TODO: Изменить апи коде

    override val message: String
        get() = "Insufficient permissions to complete the request."
}