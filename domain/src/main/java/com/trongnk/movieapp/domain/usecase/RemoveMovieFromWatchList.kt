package com.trongnk.movieapp.domain.usecase

import com.trongnk.movieapp.domain.entity.MovieInfo
import com.trongnk.movieapp.domain.repository.MovieRepository

class RemoveMovieFromWatchList(
    private val movieRepository: MovieRepository
) {
    suspend fun execute(movie: MovieInfo): Result<MovieInfo> = movieRepository.removeMovieFromWatchList(movie)
}