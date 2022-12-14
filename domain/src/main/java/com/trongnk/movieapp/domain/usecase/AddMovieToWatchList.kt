package com.trongnk.movieapp.domain.usecase

import com.trongnk.movieapp.domain.entity.MovieInfo
import com.trongnk.movieapp.domain.repository.MovieRepository
import com.trongnk.movieapp.domain.util.Result

class AddMovieToWatchList(
    private val movieRepository: MovieRepository
) {
    suspend fun execute(movie: MovieInfo): Result<MovieInfo> = movieRepository.addMovieToWatchList(movie)
}