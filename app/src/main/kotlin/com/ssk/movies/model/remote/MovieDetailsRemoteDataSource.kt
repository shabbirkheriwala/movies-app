package com.ssk.movies.model.remote

import com.ssk.movies.model.remote.BaseDataSource
import com.ssk.movies.model.remote.MoviesService
import javax.inject.Inject

class MovieDetailsRemoteDataSource @Inject constructor(
    private val moviesService: MoviesService
): BaseDataSource(){
    suspend fun getMovieDetails(movieId: Int) = getResult { moviesService.getMovieDetails(movieId) }
}