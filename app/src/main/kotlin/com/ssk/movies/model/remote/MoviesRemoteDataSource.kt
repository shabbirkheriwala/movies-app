package com.ssk.movies.model.remote

import com.ssk.movies.model.remote.BaseDataSource
import com.ssk.movies.model.remote.MoviesService
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(
    private val moviesService: MoviesService
): BaseDataSource(){
    suspend fun getPopularMovies() = getResult { moviesService.getPopularMovie() }
}