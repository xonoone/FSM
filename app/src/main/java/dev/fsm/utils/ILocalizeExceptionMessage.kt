package dev.fsm.utils

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dev.fsm.R
import dev.fsm.error.exceptions.api.UserNotFound
import dev.fsm.error.exceptions.api.UserPasswordIncorrect
import dev.fsm.error.exceptions.http.EternalError
import dev.fsm.error.exceptions.http.NotEnoughMemory
import dev.fsm.error.exceptions.jwt.AccessForbidden
import dev.fsm.error.exceptions.jwt.TokenExpired
import dev.fsm.error.exceptions.jwt.TokenNotWhitelisted
import dev.fsm.error.exceptions.jwt.Unauthorized
import dev.fsm.error.exceptions.ui.DataEmpty
import dev.fsm.error.exceptions.ui.FieldsEmpty
import dev.fsm.error.exceptions.ui.Timeout
import java.net.SocketTimeoutException

interface ILocalizeExceptionMessage {

    @Composable
    fun Exception.localized(): String

    fun Exception.localized(context: Context): String

    object LocalizeExceptionMessage : ILocalizeExceptionMessage {
        @Composable
        override fun Exception.localized(): String {
            Log.e("Error", "LocalizeExceptionMessage -> localized: ", this)
            return when (this) {
                is EternalError -> stringResource(id = R.string.error_message_eternal)
                is NotEnoughMemory -> stringResource(id = R.string.error_message_memory)
                is AccessForbidden -> stringResource(id = R.string.error_message_access)
                is Unauthorized -> stringResource(id = R.string.error_message_unauthorized)
                is TokenExpired -> stringResource(id = R.string.error_message_token_expired)
                is TokenNotWhitelisted -> stringResource(id = R.string.error_message_blocked_user)
                is Timeout -> stringResource(id = R.string.error_message_connection_timeout)
                is FieldsEmpty -> stringResource(id = R.string.error_message_fields_empty)
                is UserNotFound -> stringResource(id = R.string.error_message_user_not_founded)
                is UserPasswordIncorrect -> stringResource(id = R.string.error_message_user_password_incorrect)
                else -> stringResource(id = R.string.unexpected_error)
            }
        }

        override fun Exception.localized(context: Context): String {
            Log.e("Error", "LocalizeExceptionMessage -> localized: ", this)
            return when (this) {
                is EternalError -> context.getString(R.string.error_message_eternal)
                is NotEnoughMemory -> context.getString(R.string.error_message_memory)
                is AccessForbidden -> context.getString(R.string.error_message_access)
                is Unauthorized -> context.getString(R.string.error_message_unauthorized)
                is TokenExpired -> context.getString(R.string.error_message_token_expired)
                is TokenNotWhitelisted -> context.getString(R.string.error_message_blocked_user)
                is Timeout -> context.getString(R.string.error_message_connection_timeout)
                is FieldsEmpty -> context.getString(R.string.error_message_fields_empty)
                is UserNotFound -> context.getString(R.string.error_message_user_not_founded)
                is UserPasswordIncorrect -> context.getString(R.string.error_message_user_password_incorrect)
                else -> context.getString(R.string.unexpected_error)
            }
        }
    }
}