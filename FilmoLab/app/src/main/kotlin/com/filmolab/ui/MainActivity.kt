package com.filmolab.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import com.filmolab.ui.HomeScreen
import com.filmolab.ui.FavoritesScreen
import com.filmolab.ui.HistoryScreen
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import com.filmolab.ui.HomeScreen
import com.filmolab.ui.FavoritesScreen
import com.filmolab.ui.HistoryScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.filmolab.ui.theme.FilmoLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FilmoLabTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FilmoLabApp()
                }
            }
        }
    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorites : Screen("favorites")
    object History : Screen("history")
    object Search : Screen("search")
}

@Composable
fun FilmoLabApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            AppScaffold(navController = navController) { padding ->
                HomeScreen(navController = navController, padding = padding)
            }
        }
        composable(Screen.Favorites.route) {
            AppScaffold(navController = navController) { padding ->
                FavoritesScreen(navController = navController, padding = padding)
            }
        }
        composable(Screen.History.route) {
            AppScaffold(navController = navController) { padding ->
                HistoryScreen(navController = navController, padding = padding)
            }
        }
        composable(Screen.Search.route) {
            AppScaffold(navController = navController) { padding ->
                PlaceholderScreen(name = "Busca", padding = padding)
            }
        }
    }
}

@Composable
fun PlaceholderScreen(name: String, padding: PaddingValues) {
    // Implementação temporária para visualização
    // Será substituída pelas telas reais
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Tela de $name",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground // Branco
        )
    }
}
