package dev.fsm.data.utils

import android.util.Log
import com.google.gson.Gson
import dev.fsm.data.network.api.models.ApiResponse
import dev.fsm.data.utils.jwt.ITokenManager
import dev.fsm.data.utils.jwt.TokenResponse
import dev.fsm.domain.repository.IAuthenticationRepository
import dev.fsm.error.exceptions.jwt.JWTException
import dev.fsm.error.exceptions.jwt.TokenExpired
import dev.fsm.error.handlers.IExceptionHandler
import javax.inject.Inject

internal class Request @Inject constructor(
    private val tokenManager: ITokenManager.TokenManager,
    private val repository: IAuthenticationRepository
) {
    private val gson: Gson = Gson()

    suspend fun <T> safeApiCallWithAuth(
        call: suspend () -> retrofit2.Response<ApiResponse<T>>
    ): Response<T> {
        try {
            val response = call.invoke()
            val body = response.body()

            if (response.isSuccessful) {
                val data = body?.data
                return if (data == null)
                    Response.Success.Empty
                else
                    Response.Success.Data(data = data)
            } else {
                val errorBody = gson.fromJson(
                    response.errorBody()?.string(),
                    ApiResponse::class.java
                )
                val exceptionHandler = IExceptionHandler.ExceptionHandler(
                    httpCode = response.code(),
                    apiCode = errorBody?.apiCode
                )
                val exception = exceptionHandler.exception()
                return if (exception is TokenExpired) {
                    when (val refreshResponse = tokenManager.refresh()) {
                        is TokenResponse.Failure -> {
                            if (refreshResponse.exception is JWTException) {
                                repository.logout()
                            }
                            Response.Error(exception = refreshResponse.exception)
                        }

                        is TokenResponse.Success ->
                            safeApiCallWithAuth { call.invoke() }
                    }
                } else {
                    if (response.code() == 401 || response.code() == 403) repository.logout() // TODO: Есть константы в жабе. Лучше из них взять
                    Response.Error(exception = Exception(response.message()))
                }
            }
        } catch (e: Exception) {
            return Response.Error(exception = e)
        }
    }
}

@JvmName("requestRetrofit")
internal suspend fun <T> safeApiCall(
    call: suspend () -> retrofit2.Response<ApiResponse<T>>
): Response<ApiResponse<T>> {
    return try {
        val response = call()
        val body = response.body()
        if (response.isSuccessful) {
            if (body == null) Response.Success.Empty else Response.Success.Data(data = body)
        } else {
            val errorBody = Gson().fromJson(
                response.errorBody()?.string(),
                ApiResponse::class.java
            )
            val exceptionHandler = IExceptionHandler.ExceptionHandler(
                httpCode = response.code(),
                apiCode = errorBody?.apiCode
            )
            val exception = exceptionHandler.exception()
            Response.Error(exception = exception ?: Exception(response.message()))
        }
    } catch (e: Exception) {
        Response.Error(exception = e)
    }
}

@JvmName("requestLocal")
internal suspend fun <T> safeCall(call: suspend () -> T?): Response<T> {
    return try {
        val data = call.invoke()
        if (data != null) Response.Success.Data(data = data)
        else Response.Success.Empty
    } catch (e: Exception) {
        Response.Error(exception = e)
    }
}