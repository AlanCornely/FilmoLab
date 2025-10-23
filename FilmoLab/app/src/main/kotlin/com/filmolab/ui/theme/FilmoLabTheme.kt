package com.filmolab.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFD32F2F), // Vermelho principal (Red_Accent)
    secondary = Color(0xFF121212), // Preto secundário (Black_Secondary)
    tertiary = Color(0xFFE57373), // Vermelho claro (Red_Light)
    background = Color(0xFF000000), // Fundo preto (Black_Primary)
    surface = Color(0xFF121212), // Superfície preta (Black_Secondary)
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFD32F2F),
    secondary = Color(0xFF121212),
    tertiary = Color(0xFFE57373),
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
fun FilmoLabTheme(
    darkTheme: Boolean = true, // Forçando o tema escuro (preto e vermelho)
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme // Mesmo que o usuário mude para light, manteremos o dark para o estilo
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Será criado um arquivo Typography.kt
        content = content
    )
}
