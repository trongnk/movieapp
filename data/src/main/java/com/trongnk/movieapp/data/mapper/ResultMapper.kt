package com.trongnk.movieapp.data.mapper

import com.trongnk.movieapp.data.exception.NoContentException
import com.trongnk.movieapp.data.exception.NoNetworkException
import com.trongnk.movieapp.data.exception.UnauthorizedAccessException
import java.net.UnknownServiceException
import javax.net.ssl.SSLPeerUnverifiedException
import com.trongnk.movieapp.domain.util.Error
import com.trongnk.movieapp.domain.util.Result

fun mapError(e: Exception): Error {
    return when (e) {
        is NoNetworkException -> Error.NetworkConnection
        is UnauthorizedAccessException, is UnknownServiceException -> Error.UnauthorizedAccess
        is SSLPeerUnverifiedException -> Error.CertificatePinningError
        is NoContentException ->  Error.NoContentError
        else -> Error.GenericError(e)
    }
}


inline fun <R> resultCatching(block: () -> R): Result<R> {
    return try {
        Result.Success(block())
    } catch (e: Exception) {
        Result.Failure(mapError(e))
    }
}
