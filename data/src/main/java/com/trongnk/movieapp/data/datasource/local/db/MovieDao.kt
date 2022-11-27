package com.trongnk.movieapp.data.datasource.local.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.trongnk.movieapp.data.entity.Movie

/**
 * Data Access Object
 */
@Dao
interface MovieDao {

    /**
     * Observes list of movies.
     *
     * @return all movies.
     */
    @Query("SELECT * FROM movie")
    fun observeMovies(): LiveData<List<Movie>>

    /**
     * Select all movies from the movie table.
     *
     * @return all movies.
     */
    @Query("SELECT * FROM movie")
    suspend fun getMovies(): List<Movie>

    /**
     * Get list of weathers by title.
     *
     * @param title the title.
     * @return movies.
     */
    @Query("SELECT * FROM movie WHERE title = :title")
    fun getMoviesByTitle(title: String): List<Movie>

    /**
     * Insert a movie in the database. If the movie already exists, replace it.
     *
     * @param movie the movie to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    /**
     * Update a movie.
     *
     * @param movie movie to be updated
     * @return the number of movies updated. This should always be 1.
     */
    @Update
    suspend fun updateMovie(movie: Movie): Int

    /**
     * Delete all movies.
     */
    @Query("DELETE FROM movie")
    suspend fun deleteMovies()
}
