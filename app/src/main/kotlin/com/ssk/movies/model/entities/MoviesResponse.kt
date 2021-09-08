package com.ssk.movies.model.entities

import com.google.gson.annotations.SerializedName

/**
 * Data class for movies list
 */
data class MoviesResponse(
    @SerializedName("results")
    val moviesList: List<Movie>
)
