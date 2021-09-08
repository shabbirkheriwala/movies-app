package com.ssk.movies.model.repositories

import com.ssk.movies.model.entities.MovieDetails
import com.ssk.movies.model.local.MoviesDetailsDao
import com.ssk.movies.model.remote.MovieDetailsRemoteDataSource
import com.ssk.movies.util.performDataOperation
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(
    private val movieDetailsRemoteDataSource: MovieDetailsRemoteDataSource,
    private val moviesDetailsDao: MoviesDetailsDao
) {
    fun getMovieDetails(movieId: Int) = performDataOperation(
        dbQuery = { moviesDetailsDao.getMovie(movieId) },
        networkCall = { movieDetailsRemoteDataSource.getMovieDetails(movieId) },
        saveCallResult = {
            moviesDetailsDao.insertMovie(
                MovieDetails(
                    it.id,
                    it.overview,
                    it.posterPath,
                    it.releaseDate,
                    it.title,
                    it.rating
                )
            )
        }
    )
}