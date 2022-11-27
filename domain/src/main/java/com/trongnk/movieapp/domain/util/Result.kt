package com.trongnk.movieapp.domain.util

sealed class Result<out T> {

    data class Success<T>(val data: T) : Result<T>()
    data class Failure<T>(val error: Error) : Result<T>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Failure -> "Error[exception=$error]"
        }
    }

    fun handle(
        onSuccess: (T) -> Unit,
        onError: (Error) -> Unit,
        doBefore: () -> Unit = {},
        doAfter: () -> Unit = {}
    ) {
        doBefore()

        when (this) {
            is Success -> onSuccess(data)
            is Failure -> onError(error)
        }

        doAfter()
    }
}

inline fun <T, R> Result<T>.getResult(
    success: (Result.Success<T>) -> R,
    error: (Result.Failure<T>) -> R
): R = if (this is Result.Success) success(this) else error(this as Result.Failure)