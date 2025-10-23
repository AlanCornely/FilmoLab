package com.filmolab.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filmolab.model.MovieSummary
import com.filmolab.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class HomeViewModel : ViewModel() {

    // Estado para os filmes de "Lançamentos"
    private val _newReleases = MutableStateFlow<List<MovieSummary>>(emptyList())
    val newReleases: StateFlow<List<MovieSummary>> = _newReleases

    // Estado para os filmes de "Gêneros Mais Assistidos" (Simulação)
    private val _genreMovies = MutableStateFlow<Map<String, MovieSummary>>(emptyMap())
    val genreMovies: StateFlow<Map<String, MovieSummary>> = _genreMovies

    // Estado de carregamento
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadNewReleases()
        loadGenreMoviesSimulation()
    }

    private fun loadNewReleases() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()
                // Simulação de "Lançamentos" buscando filmes genéricos do ano atual
                val response = RetrofitClient.omdbApi.searchMovies(year = currentYear)
                
                if (response.response == "True" && response.search != null) {
                    _newReleases.value = response.search.filter { it.poster != "N/A" }
                } else {
                    // Tenta o ano anterior se não houver resultados no ano atual
                    val lastYear = (currentYear.toInt() - 1).toString()
                    val fallbackResponse = RetrofitClient.omdbApi.searchMovies(year = lastYear)

                    if (fallbackResponse.response == "True" && fallbackResponse.search != null) {
                        _newReleases.value = fallbackResponse.search.filter { it.poster != "N/A" }
                    }
                }
            } catch (e: Exception) {
                // Tratar erro de rede
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun loadGenreMoviesSimulation() {
        // Simulação de "Gêneros Mais Assistidos" com títulos hardcoded
        // Para cada título, faremos uma busca por ID para obter o poster e detalhes (se necessário)
        val genreMap = mapOf(
            "Ação" to "tt0468569", // The Dark Knight
            "Ficção Científica" to "tt0816692", // Interstellar
            "Comédia" to "tt0820300", // Superbad
            "Drama" to "tt0111161" // The Shawshank Redemption
        )

        viewModelScope.launch {
            val movieMap = mutableMapOf<String, MovieSummary>()
            genreMap.forEach { (genre, imdbId) ->
                try {
                    // A OMDb API não tem um endpoint de busca por gênero, então buscamos por ID
                    val detail = RetrofitClient.omdbApi.getMovieDetail(imdbId)
                    if (detail.response == "True") {
                        movieMap[genre] = MovieSummary(
                            title = detail.title,
                            year = detail.year,
                            imdbID = detail.imdbID,
                            type = detail.type,
                            poster = detail.poster
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            _genreMovies.value = movieMap
        }
    }
}
