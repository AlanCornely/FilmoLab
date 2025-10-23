package com.filmolab.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FavoriteMovie(
    @PrimaryKey
    val imdbID: String,
    val title: String,
    val year: String,
    val poster: String,
    val addedDate: Long = System.currentTimeMillis()
)

@Entity(tableName = "history_movies")
data class HistoryMovie(
    @PrimaryKey
    val imdbID: String,
    val title: String,
    val year: String,
    val poster: String,
    val viewDate: Long = System.currentTimeMillis()
)
