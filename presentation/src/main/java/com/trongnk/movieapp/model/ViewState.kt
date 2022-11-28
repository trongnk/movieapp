package com.trongnk.movieapp.model


import com.trongnk.movieapp.domain.util.Error

sealed class ViewState<T> {
    data class Loading<T>(val data: T?) : ViewState<T>()
    data class Data<T>(val data: T) : ViewState<T>()
    data class Failure<T>(val error: Error) : ViewState<T>()
}