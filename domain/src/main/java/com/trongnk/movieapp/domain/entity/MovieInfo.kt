package com.trongnk.movieapp.domain.entity

import java.util.Date

data class MovieInfo(
    val title: String,
    val description: String,
    val rating: Float,
    val duration: String,
    val genre: String,
    val releaseDate: Date?,
    val trailerLink: String,
    val poster: String,
    val watchList: Boolean
)
