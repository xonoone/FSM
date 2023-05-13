package dev.fsm.data.utils.jwt

import dev.fsm.data.network.api.annotations.Authenticated
import dev.fsm.data.network.api.annotations.UserID
import dev.fsm.data.storage.room.TokenStorageRoom
import dev.fsm.data.utils.config.NetworkConfig.API.ACCESS_TOKEN_HEADER_TYPE
import dev.fsm.data.utils.config.NetworkConfig.API.ACCESS_TOKEN_PREFIX
import dev.fsm.data.utils.config.NetworkConfig.API.QUERY_USER_ID
import dev.fsm.error.exceptions.jwt.Unauthorized

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import javax.inject.Inject
import dev.fsm.data.utils.Response as DataResponse

class AuthInterceptor @Inject constructor(
    private val localStorage: TokenStorageRoom,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val invocation = chain.request().tag(Invocation::class.java)
            ?: return chain.proceed(chain.request())

        val shouldAttachAuthHeader = invocation
            .method()
            .annotations
            .any { it.annotationClass == Authenticated::class }

        val shouldAttachUserIDQuery = invocation
            .method()
            .annotations
            .any { it.annotationClass == UserID::class }


        return if (shouldAttachAuthHeader or shouldAttachUserIDQuery) {
            val responseToken = runBlocking { localStorage.get() }
            val token = when (responseToken) {
                is DataResponse.Error -> throw responseToken.exception
                is DataResponse.Success.Data -> responseToken.data
                is DataResponse.Success.Empty -> throw Unauthorized()
            }

            val request = chain.request()
            val requestBuilder = request.newBuilder()

            if (shouldAttachUserIDQuery)
                requestBuilder.url(
                    request.url
                        .newBuilder()
                        .addQueryParameter(QUERY_USER_ID, token.userId)
                        .build()
                )
            if (shouldAttachAuthHeader)
                requestBuilder.addHeader(
                    ACCESS_TOKEN_HEADER_TYPE,
                    ACCESS_TOKEN_PREFIX + token.accessToken
                )
            chain.proceed(requestBuilder.build())
        } else {
            chain.proceed(chain.request())
        }
    }
}