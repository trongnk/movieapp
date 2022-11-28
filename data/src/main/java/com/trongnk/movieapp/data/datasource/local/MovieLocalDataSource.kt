package com.trongnk.movieapp.data.datasource.local

import com.google.gson.Gson
import com.trongnk.movieapp.data.datasource.MovieDataSource
import com.trongnk.movieapp.data.datasource.local.db.MovieDao
import com.trongnk.movieapp.data.entity.MovieResponse
import com.trongnk.movieapp.data.mapper.resultCatching
import com.trongnk.movieapp.data.mapper.toMovie
import com.trongnk.movieapp.data.mapper.toMovieInfo
import com.trongnk.movieapp.data.mapper.toMovies
import com.trongnk.movieapp.data.util.open
import com.trongnk.movieapp.domain.entity.MovieInfo
import com.trongnk.movieapp.domain.util.Result

class MovieLocalDataSource(
    private val movieDao: MovieDao
) : MovieDataSource.Local {
    override suspend fun getMovieList(): Result<List<MovieInfo>> =
        resultCatching {
            var movies = movieDao.getMovies()
            if (movies.isEmpty()) {
                movies = this.javaClass.classLoader
                    .open("movies.json")
                    .use {
                        Gson().fromJson(it, MovieResponse::class.java)
                    }.toMovies()

                movies.forEach {
                    movieDao.insertMovie(it)
                }
            }
            movies.map { it.toMovieInfo() }
        }

    override suspend fun getMovieDetail(title: String): Result<MovieInfo?> = resultCatching {
        movieDao.getMoviesByTitle(title).firstOrNull()?.toMovieInfo()

    }

    override suspend fun addMovieToWatchList(movie: MovieInfo): Result<MovieInfo> =
        resultCatching {
            movieDao.updateMovie(movie.toMovie().apply { watchList = true })
            movieDao.getMoviesByTitle(movie.title).firstOrNull()?.toMovieInfo() ?: movie
        }

    override suspend fun removeMovieFromWatchList(movie: MovieInfo): Result<MovieInfo> =
        resultCatching {
            movieDao.updateMovie(movie.toMovie().apply { watchList = false })
            movieDao.getMoviesByTitle(movie.title).firstOrNull()?.toMovieInfo() ?: movie
        }

}
