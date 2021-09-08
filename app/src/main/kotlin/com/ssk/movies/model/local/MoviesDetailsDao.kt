package com.ssk.movies.model.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssk.movies.model.entities.Movie
import com.ssk.movies.model.entities.MovieDetails

@Dao
interface MoviesDetailsDao {

    @Query("SELECT * FROM movies_details WHERE id = :movieId")
    fun getMovie(movieId: Int): LiveData<MovieDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieDetails)

}