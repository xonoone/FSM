package dev.fsm.error.exceptions

import java.net.ConnectException

open class ServerException(val httpCode: Int?, val apiCode: Int?) : ConnectException() {
    constructor() : this(httpCode = -1, apiCode = -1)
}