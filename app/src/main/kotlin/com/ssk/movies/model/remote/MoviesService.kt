package com.ssk.movies.model.remote

import com.ssk.movies.model.entities.MovieDetailsResponse
import com.ssk.movies.model.entities.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesService {

    @GET("movie/popular")
    suspend fun getPopularMovie(): Response<MoviesResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") id: Int): Response<MovieDetailsResponse>
}