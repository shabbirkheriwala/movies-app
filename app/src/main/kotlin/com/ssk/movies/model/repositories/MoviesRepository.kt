package com.ssk.movies.model.repositories

import com.ssk.movies.model.local.MoviesDao
import com.ssk.movies.model.remote.MoviesRemoteDataSource
import com.ssk.movies.util.performDataOperation
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    private val moviesDao: MoviesDao
) {

    fun getPopularMovies() = performDataOperation(
        dbQuery = { moviesDao.getPopularMovies()},
        networkCall = { moviesRemoteDataSource.getPopularMovies()},
        saveCallResult = { moviesDao.insertAll(it.moviesList)}
    )
}
