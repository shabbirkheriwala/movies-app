package com.ssk.movies.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
* Entity class representing the database table for storing movies details
* */
@Entity(tableName = "movies_details")
data class MovieDetails(
    @PrimaryKey
    val id: Int,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double
)