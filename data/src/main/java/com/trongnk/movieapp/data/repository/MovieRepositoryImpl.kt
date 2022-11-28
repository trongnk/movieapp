package com.trongnk.movieapp.data.repository

import com.trongnk.movieapp.data.datasource.MovieDataSource
import com.trongnk.movieapp.domain.entity.MovieInfo
import com.trongnk.movieapp.domain.repository.MovieRepository
import com.trongnk.movieapp.domain.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepositoryImpl(
    //private val remoteDataSource: MovieDataSource.Remote,
    private val localDataSource: MovieDataSource.Local,
    //private val cache: MovieDataSource.Cache,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MovieRepository {

    override suspend fun getMovieList(): Result<List<MovieInfo>> = withContext(ioDispatcher) {
        localDataSource.getMovieList()
    }

    override suspend fun getMovieDetail(title: String): Result<MovieInfo?> = withContext(ioDispatcher) {
        localDataSource.getMovieDetail(title)
    }

    override suspend fun addMovieToWatchList(movie: MovieInfo): Result<MovieInfo> = withContext(ioDispatcher) {
        localDataSource.addMovieToWatchList(movie)
    }

    override suspend fun removeMovieFromWatchList(movie: MovieInfo): Result<MovieInfo> = withContext(ioDispatcher) {
        localDataSource.removeMovieFromWatchList(movie)
    }

}
