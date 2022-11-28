package com.trongnk.movieapp.data.datasource

import com.trongnk.movieapp.domain.entity.MovieInfo
import com.trongnk.movieapp.domain.util.Result

interface MovieDataSource {

    interface Remote {
        suspend fun getMovieList(): Result<List<MovieInfo>>
    }

    interface Local {
        suspend fun getMovieList(): Result<List<MovieInfo>>
        suspend fun getMovieDetail(title: String): Result<MovieInfo?>
        suspend fun addMovieToWatchList(movie: MovieInfo): Result<MovieInfo>
        suspend fun removeMovieFromWatchList(movie: MovieInfo): Result<MovieInfo>
    }

    interface Cache : Local
}
