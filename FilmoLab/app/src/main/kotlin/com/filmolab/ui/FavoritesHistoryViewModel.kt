package com.filmolab.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.filmolab.model.AppDatabase
import com.filmolab.model.FavoriteMovie
import com.filmolab.model.HistoryMovie
import com.filmolab.model.MovieDao
import com.filmolab.model.MovieSummary
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesHistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val movieDao: MovieDao

    init {
        val db = AppDatabase.getDatabase(application)
        movieDao = db.movieDao()
    }

    // Favoritos
    val favorites: StateFlow<List<FavoriteMovie>> = movieDao.getAllFavorites()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addToFavorites(movie: MovieSummary) {
        viewModelScope.launch {
            val favorite = FavoriteMovie(
                imdbID = movie.imdbID,
                title = movie.title,
                year = movie.year,
                poster = movie.poster
            )
            movieDao.insertFavorite(favorite)
        }
    }

    fun removeFromFavorites(movie: FavoriteMovie) {
        viewModelScope.launch {
            movieDao.deleteFavorite(movie)
        }
    }

    fun isFavorite(imdbID: String): StateFlow<Boolean> = movieDao.isFavorite(imdbID)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    // Histórico
    val history: StateFlow<List<HistoryMovie>> = movieDao.getAllHistory()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addToHistory(movie: MovieSummary) {
        viewModelScope.launch {
            val historyItem = HistoryMovie(
                imdbID = movie.imdbID,
                title = movie.title,
                year = movie.year,
                poster = movie.poster
            )
            movieDao.insertHistory(historyItem)
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            movieDao.clearHistory()
        }
    }
}
