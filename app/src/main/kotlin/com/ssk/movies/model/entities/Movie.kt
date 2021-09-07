package com.ssk.movies.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
* Entity class representing the database table for storing movies
* */
@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    val id: Int,
    val title: String,
    val poster_path: String
)