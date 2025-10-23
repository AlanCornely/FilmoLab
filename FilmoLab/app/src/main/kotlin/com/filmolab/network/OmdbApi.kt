package com.filmolab.network

import com.filmolab.model.MovieDetail
import com.filmolab.model.MovieSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {

    // Chave da API fornecida pelo usuário
    companion object {
        const val API_KEY = "13fe47be"
    }

    // Busca por título (s) ou por ano (y) para simular lançamentos
    @GET("/")
    suspend fun searchMovies(
        @Query("s") searchTitle: String = "movie", // Busca genérica para simular lançamentos
        @Query("y") year: String? = null, // Ano opcional para filtrar lançamentos
        @Query("type") type: String = "movie", // Apenas filmes
        @Query("page") page: Int = 1,
        @Query("apikey") apiKey: String = API_KEY
    ): MovieSearchResponse

    // Busca por ID para obter detalhes do filme
    @GET("/")
    suspend fun getMovieDetail(
        @Query("i") imdbId: String,
        @Query("plot") plot: String = "full",
        @Query("apikey") apiKey: String = API_KEY
    ): MovieDetail
}
