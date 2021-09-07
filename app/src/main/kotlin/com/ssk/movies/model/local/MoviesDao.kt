package com.ssk.movies.model.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssk.movies.model.entities.Movie

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies")
    fun getPopularMovies() : LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movie: List<Movie>)

}