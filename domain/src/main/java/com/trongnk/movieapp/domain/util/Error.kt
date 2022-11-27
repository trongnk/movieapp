package com.trongnk.movieapp.domain.util

abstract class Error {
    object UnauthorizedAccess : Error()
    object NetworkConnection : Error()
    object CertificatePinningError : Error()
    object NoContentError : Error()
    data class GenericError(val exception: Exception) : Error()
}