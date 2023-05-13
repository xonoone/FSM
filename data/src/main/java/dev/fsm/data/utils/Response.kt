package dev.fsm.data.utils

sealed class Response<out T> {
    sealed class Success<out T> : Response<T>() {
        data class Data<out T>(val data: T) : Success<T>()
        object Empty : Success<Nothing>()
    }
    data class Error(val exception: Exception) : Response<Nothing>()
}