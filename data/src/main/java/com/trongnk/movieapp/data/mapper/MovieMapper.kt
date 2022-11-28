package com.trongnk.movieapp.data.mapper

import com.trongnk.movieapp.data.entity.Movie
import com.trongnk.movieapp.data.entity.MovieRemote
import com.trongnk.movieapp.data.entity.MovieResponse
import com.trongnk.movieapp.data.util.Constants
import com.trongnk.movieapp.domain.entity.MovieInfo
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun MovieResponse.toMovies(): List<Movie> = movies.map { it.toMovie() }

fun MovieRemote.toMovie() = Movie(
    title = title,
    description = description,
    rating = rating,
    duration = duration,
    genre = genre,
    releaseDate = releaseDate,
    trailerLink = trailerLink,
    poster = poster,
    watchList = watchList
)

fun Movie.toMovieInfo() = MovieInfo(
    title = title,
    description = description,
    rating = rating,
    duration = duration,
    genre = genre,
    releaseDate = SimpleDateFormat(Constants.RELEASE_DATE_FORMAT, Locale.US).parse(releaseDate),
    trailerLink = trailerLink,
    poster = poster,
    watchList = watchList
)

fun MovieInfo.toMovie() = Movie(
    title = title,
    description = description,
    rating = rating,
    duration = duration,
    genre = genre,
    releaseDate = releaseDate?.let{ SimpleDateFormat(Constants.RELEASE_DATE_FORMAT, Locale.US).format(it) } ?: "",
    trailerLink = trailerLink,
    poster = poster,
    watchList = watchList
)

