package com.ssk.movies.model.entities

import com.google.gson.annotations.SerializedName

/**
 * Data class for movie details
 */
data class MovieDetailsResponse(
    val id: Int,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String,
    @SerializedName("vote_average")
    val rating: Double
)