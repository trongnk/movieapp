package com.trongnk.movieapp.domain.usecase

import com.trongnk.movieapp.domain.entity.MovieInfo
import com.trongnk.movieapp.domain.repository.MovieRepository
import com.trongnk.movieapp.domain.util.Result

class GetMovieDetail(
    private val movieRepository: MovieRepository
) {
    suspend fun execute(title: String): Result<MovieInfo?> = movieRepository.getMovieDetail(title)
}