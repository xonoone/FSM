package dev.fsm.error.handlers

import dev.fsm.error.exceptions.api.UserNotFound
import dev.fsm.error.exceptions.api.UserPasswordIncorrect
import dev.fsm.error.exceptions.http.EternalError
import dev.fsm.error.exceptions.http.NotEnoughMemory
import dev.fsm.error.exceptions.jwt.AccessForbidden
import dev.fsm.error.exceptions.jwt.TokenExpired
import dev.fsm.error.exceptions.jwt.TokenNotWhitelisted
import dev.fsm.error.exceptions.jwt.Unauthorized
import dev.fsm.error.utils.Codes
import dev.fsm.error.utils.Codes.Api
import dev.fsm.error.utils.Codes.Http.ACCESS.ACCESS_FORBIDDEN
import dev.fsm.error.utils.Codes.Http.ACCESS.UNAUTHORIZED
import dev.fsm.error.utils.Codes.Http.ETERNAL
import dev.fsm.error.utils.Codes.Http.ETERNAL.NOT_ENOUGH_MEMORY
import dev.fsm.error.utils.Codes.Http.OK
import java.net.UnknownHostException
import java.net.UnknownServiceException
import dev.fsm.error.utils.Codes.Api.ACCESS.TOKEN_EXPIRED as API_TOKEN_EXPIRED
import dev.fsm.error.utils.Codes.Api.ACCESS.USER_IS_BLOCKED as API_USER_BLOCKED
import dev.fsm.error.utils.Codes.Api.USER.USER_NOT_FOUND as API_USER_NOT_FOUND
import dev.fsm.error.utils.Codes.Api.USER.INCORRECT_PASSWORD as API_USER_PASSWORD_INCORRECT
import dev.fsm.error.utils.Codes.Http.ACCESS.TOKEN_EXPIRED as HTTP_TOKEN_EXPIRED
import dev.fsm.error.utils.Codes.Http.ACCESS.USER_IS_BLOCKED as HTTP_USER_BLOCKED

interface IExceptionHandler {

    fun exception(): Exception?

    class ExceptionHandler(
        private val httpCode: Int? = Codes.UNKNOWN_ERROR,
        private val apiCode: Int? = Codes.UNKNOWN_ERROR,
    ) : IExceptionHandler {

        private val isHttpError = httpCode !in OK.start..OK.end
        private val isApiError = apiCode != Api.OK

        override fun exception(): Exception? {
            if (isHttpError and isApiError) {
                if ((httpCode == HTTP_TOKEN_EXPIRED) and (apiCode == API_TOKEN_EXPIRED))
                    return TokenExpired()
                if ((httpCode == HTTP_USER_BLOCKED) and (apiCode == API_USER_BLOCKED))
                    return TokenNotWhitelisted()
                if ((httpCode == UNAUTHORIZED) and (apiCode == API_USER_NOT_FOUND)) {
                    return UserNotFound()
                }
                if ((httpCode == UNAUTHORIZED) and (apiCode == API_USER_PASSWORD_INCORRECT)) {
                    return UserPasswordIncorrect()
                }
                if ((httpCode == UNAUTHORIZED) and (apiCode != null)) {
                    return Unauthorized()
                }
                if (httpCode == ACCESS_FORBIDDEN)
                    return AccessForbidden()
            }
            if (isHttpError) {
                return when (httpCode) {
                    in ETERNAL.start..ETERNAL.end -> {
                        if (httpCode == NOT_ENOUGH_MEMORY) NotEnoughMemory()
                        EternalError()
                    }
                    UNAUTHORIZED -> Unauthorized()
                    else -> UnknownHostException(httpCode.toString())
                }
            }
            if (isApiError) {
                return when (apiCode) {
                    API_USER_NOT_FOUND -> UserNotFound()
                    API_USER_PASSWORD_INCORRECT -> UserPasswordIncorrect()
                    else -> UnknownServiceException(apiCode.toString())
                }
            }
            return null
        }
    }
}