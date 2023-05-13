package dev.fsm.error.exceptions.jwt

import dev.fsm.error.exceptions.ServerException

open class JWTException(httpCode: Int?, apiCode: Int?) :
    ServerException(httpCode = httpCode, apiCode = apiCode)