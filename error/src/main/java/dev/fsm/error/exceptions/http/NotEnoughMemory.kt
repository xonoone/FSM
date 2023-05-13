package dev.fsm.error.exceptions.http

import dev.fsm.error.exceptions.ServerException
import dev.fsm.error.utils.Codes.Http.ETERNAL.NOT_ENOUGH_MEMORY

open class NotEnoughMemory : ServerException {
    constructor() : super(httpCode = NOT_ENOUGH_MEMORY, apiCode = null)

    override val message: String
        get() = "The data being uploaded is too large to be stored on the server."
}