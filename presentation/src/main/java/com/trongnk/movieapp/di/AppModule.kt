package com.trongnk.movieapp.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.trongnk.movieapp.data.datasource.MovieDataSource
import com.trongnk.movieapp.data.datasource.local.MovieLocalDataSource
import com.trongnk.movieapp.data.datasource.local.db.MovieDatabase
import com.trongnk.movieapp.data.repository.MovieRepositoryImpl
import com.trongnk.movieapp.domain.repository.MovieRepository
import com.trongnk.movieapp.domain.usecase.AddMovieToWatchList
import com.trongnk.movieapp.domain.usecase.GetMovieDetail
import com.trongnk.movieapp.domain.usecase.GetMovieList
import com.trongnk.movieapp.domain.usecase.RemoveMovieFromWatchList
import com.trongnk.movieapp.util.DefaultDispatcherProvider
import com.trongnk.movieapp.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalMovieDataSource

    @Singleton
    @LocalMovieDataSource
    @Provides
    fun provideMovieLocalDataSource(
        database: MovieDatabase,
    ): MovieDataSource.Local = MovieLocalDataSource(database.movieDao())

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java,
            "movie.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDispatchers(dispatcherProvider: DefaultDispatcherProvider): DispatcherProvider = dispatcherProvider

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideGetMovieListUseCase(movieRepository: MovieRepository): GetMovieList {
        return GetMovieList(movieRepository)
    }

    @Provides
    @Singleton
    fun provideGetMovieDetailUseCase(movieRepository: MovieRepository): GetMovieDetail {
        return GetMovieDetail(movieRepository)
    }

    @Provides
    @Singleton
    fun provideAddMovieToWatchListUseCase(movieRepository: MovieRepository): AddMovieToWatchList {
        return AddMovieToWatchList(movieRepository)
    }

    @Provides
    @Singleton
    fun provideRemoveMovieFromWatchListUseCase(movieRepository: MovieRepository): RemoveMovieFromWatchList {
        return RemoveMovieFromWatchList(movieRepository)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object MovieRepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        @AppModule.LocalMovieDataSource localDataSource: MovieDataSource.Local,
        dispatcherProvider: DispatcherProvider
    ): MovieRepository {
        return MovieRepositoryImpl(localDataSource,dispatcherProvider.io())
    }
}
