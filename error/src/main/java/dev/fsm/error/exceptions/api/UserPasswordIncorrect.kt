package dev.fsm.error.exceptions.api

import dev.fsm.error.exceptions.jwt.JWTException
import dev.fsm.error.utils.Codes.Api.USER.INCORRECT_PASSWORD

open class UserPasswordIncorrect : JWTException {
    constructor() : super(httpCode = null, apiCode = INCORRECT_PASSWORD)

    override val message: String
        get() = "Password is incorrect."
}