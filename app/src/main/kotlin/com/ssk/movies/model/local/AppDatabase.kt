package com.ssk.movies.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ssk.movies.model.entities.Movie
import com.ssk.movies.model.entities.MovieDetails

@Database(entities = [Movie::class, MovieDetails::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun moviesDao() : MoviesDao
    abstract fun movieDetailsDao() : MoviesDetailsDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "TMDB")
                .build()
    }
}