package com.trongnk.movieapp.domain.repository

import com.trongnk.movieapp.domain.entity.MovieInfo

interface MovieRepository {
    suspend fun getMovieList(): Result<List<MovieInfo>>
    suspend fun addMovieToWatchList(movie: MovieInfo): Result<MovieInfo>
    suspend fun removeMovieFromWatchList(movie: MovieInfo): Result<MovieInfo>
}