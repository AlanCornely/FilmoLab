package com.filmolab.model

import com.google.gson.annotations.SerializedName

data class MovieSearchResponse(
    @SerializedName("Search")
    val search: List<MovieSummary>?,
    @SerializedName("totalResults")
    val totalResults: String?,
    @SerializedName("Response")
    val response: String, // "True" or "False"
    @SerializedName("Error")
    val error: String?
)

data class MovieSummary(
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: String,
    @SerializedName("imdbID")
    val imdbID: String,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Poster")
    val poster: String // URL do poster
)
