package com.trongnk.movieapp.domain.entity

data class MovieInfo(
    val title: String,
    val description: String,
    val rating: Float,
    val duration: String,
    val genre: String,
    val releaseDate: String,
    val trailerLink: String,
    val poster: String,
    val watchList: Boolean
)
