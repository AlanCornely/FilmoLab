package com.filmolab.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    // Favoritos
    @Query("SELECT * FROM favorite_movies ORDER BY addedDate DESC")
    fun getAllFavorites(): Flow<List<FavoriteMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(movie: FavoriteMovie)

    @Delete
    suspend fun deleteFavorite(movie: FavoriteMovie)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_movies WHERE imdbID = :imdbID LIMIT 1)")
    fun isFavorite(imdbID: String): Flow<Boolean>

    // Histórico
    @Query("SELECT * FROM history_movies ORDER BY viewDate DESC")
    fun getAllHistory(): Flow<List<HistoryMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(movie: HistoryMovie)

    @Query("DELETE FROM history_movies")
    suspend fun clearHistory()
}
