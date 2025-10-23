package com.filmolab.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.filmolab.model.MovieSummary

@Composable
fun HomeScreen(
    navController: NavController,
    padding: PaddingValues,
    viewModel: HomeViewModel = viewModel()
) {
    val newReleases by viewModel.newReleases.collectAsState()
    val genreMovies by viewModel.genreMovies.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(MaterialTheme.colorScheme.background)
    ) {
        item {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            } else {
                // 1. Filmes Novos/Lançamentos
                MovieSection(
                    title = "Lançamentos (${newReleases.size})",
                    movies = newReleases,
                    onMovieClick = { /* TODO: Navegar para detalhes do filme */ }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))

            // 2. Gêneros Mais Assistidos (Simulação)
            GenreSection(
                title = "Gêneros",
                genreMovies = genreMovies,
                onGenreClick = { genre ->
                    // TODO: Implementar navegação para busca expandida
                    println("Clicou no gênero: $genre")
                }
            )
        }
        // Adicionar mais seções conforme necessário
    }
}

@Composable
fun MovieSection(
    title: String,
    movies: List<MovieSummary>,
    onMovieClick: (MovieSummary) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(movies) { movie ->
                MovieCard(movie = movie, onMovieClick = onMovieClick)
            }
        }
    }
}

@Composable
fun MovieCard(
    movie: MovieSummary,
    onMovieClick: (MovieSummary) -> Unit
) {
    Column(
        modifier = Modifier
            .width(120.dp)
            .clickable { onMovieClick(movie) },
        horizontalAlignment = Alignment.Start
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = movie.poster),
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(width = 120.dp, height = 180.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray) // Placeholder
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = movie.year,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun GenreSection(
    title: String,
    genreMovies: Map<String, MovieSummary>,
    onGenreClick: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(genreMovies.keys.toList()) { genre ->
                val movie = genreMovies[genre]
                if (movie != null) {
                    GenreCard(
                        genre = genre,
                        movie = movie,
                        onGenreClick = onGenreClick
                    )
                }
            }
        }
    }
}

@Composable
fun GenreCard(
    genre: String,
    movie: MovieSummary,
    onGenreClick: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary // Preto mais claro
        ),
        modifier = Modifier
            .width(160.dp)
            .height(80.dp)
            .clickable { onGenreClick(genre) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = genre,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary, // Vermelho
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "Expandir $genre",
                tint = MaterialTheme.colorScheme.primary, // Vermelho
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
