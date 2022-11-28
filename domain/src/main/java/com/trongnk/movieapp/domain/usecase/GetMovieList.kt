package com.trongnk.movieapp.domain.usecase

import com.trongnk.movieapp.domain.entity.MovieInfo
import com.trongnk.movieapp.domain.repository.MovieRepository
import com.trongnk.movieapp.domain.util.Result

class GetMovieList(
    private val movieRepository: MovieRepository
) {
    suspend fun execute(): Result<List<MovieInfo>> = movieRepository.getMovieList()
}