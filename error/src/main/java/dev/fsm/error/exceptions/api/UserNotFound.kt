package dev.fsm.error.exceptions.api

import dev.fsm.error.exceptions.jwt.JWTException
import dev.fsm.error.utils.Codes.Api.USER.USER_NOT_FOUND

open class UserNotFound : JWTException {
    constructor() : super(httpCode = null, apiCode = USER_NOT_FOUND)

    override val message: String
        get() = "User not found."
}