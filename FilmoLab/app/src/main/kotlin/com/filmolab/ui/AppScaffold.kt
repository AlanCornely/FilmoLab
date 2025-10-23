package com.filmolab.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.filmolab.R
import kotlinx.coroutines.launch

@Composable
fun AppScaffold(
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val currentRoute = navController.currentBackStackEntryFlow.collectAsState(initial = navController.currentBackStackEntry).value?.destination?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                currentRoute = currentRoute,
                onDestinationClicked = { route ->
                    scope.launch {
                        drawerState.close()
                    }
                    navController.navigate(route) {
                        // Evita múltiplos itens na pilha de navegação para a mesma tela
                        popUpTo(Screen.Home.route) {
                            saveState = true
                        }
                        // Evita que a tela seja recriada quando re-selecionada
                        launchSingleTop = true
                        // Restaura o estado quando volta para a tela
                        restoreState = true
                    }
                }
            )
        },
        content = {
            Scaffold(
                topBar = {
                    TopBar(
                        onMenuClicked = {
                            scope.launch {
                                drawerState.open()
                            }
                        },
                        onSearchClicked = {
                            navController.navigate(Screen.Search.route)
                        }
                    )
                },
                content = content
            )
        }
    )
}

@Composable
fun TopBar(onMenuClicked: () -> Unit, onSearchClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                color = MaterialTheme.colorScheme.primary, // Vermelho
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = onMenuClicked) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu",
                    tint = MaterialTheme.colorScheme.onBackground // Branco
                )
            }
        },
        actions = {
            // Caixa de pesquisa de borda arredondada
            Row(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .width(200.dp) // Ajuste o tamanho conforme necessário
                    .clickable { onSearchClicked() } // Clicar na caixa leva para a tela de busca
                    .background(
                        color = MaterialTheme.colorScheme.secondary, // Preto mais claro
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Pesquisar",
                    tint = MaterialTheme.colorScheme.primary, // Vermelho
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.search_hint),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f), // Vermelho mais suave
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background // Fundo Preto
        )
    )
}

@Composable
fun DrawerContent(
    currentRoute: String?,
    onDestinationClicked: (route: String) -> Unit
) {
    ModalDrawerSheet(
        drawerContainerColor = MaterialTheme.colorScheme.secondary // Fundo do menu preto mais claro
    ) {
        // Cabeçalho do Drawer
        Text(
            text = "Navegação",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary // Vermelho
        )
        Divider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))

        // Itens de navegação
        DrawerItem(
            icon = Icons.Filled.Home,
            label = stringResource(R.string.menu_home),
            route = Screen.Home.route,
            isSelected = currentRoute == Screen.Home.route,
            onClick = onDestinationClicked
        )
        DrawerItem(
            icon = Icons.Filled.Star,
            label = stringResource(R.string.menu_favorites),
            route = Screen.Favorites.route,
            isSelected = currentRoute == Screen.Favorites.route,
            onClick = onDestinationClicked
        )
        DrawerItem(
            icon = Icons.Filled.History,
            label = stringResource(R.string.menu_history),
            route = Screen.History.route,
            isSelected = currentRoute == Screen.History.route,
            onClick = onDestinationClicked
        )
    }
}

@Composable
fun DrawerItem(
    icon: ImageVector,
    label: String,
    route: String,
    isSelected: Boolean,
    onClick: (route: String) -> Unit
) {
    NavigationDrawerItem(
        icon = { Icon(icon, contentDescription = label) },
        label = { Text(label) },
        selected = isSelected,
        onClick = { onClick(route) },
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), // Vermelho transparente
            unselectedContainerColor = Color.Transparent,
            selectedIconColor = MaterialTheme.colorScheme.primary, // Vermelho
            unselectedIconColor = MaterialTheme.colorScheme.onSecondary, // Branco
            selectedTextColor = MaterialTheme.colorScheme.primary, // Vermelho
            unselectedTextColor = MaterialTheme.colorScheme.onSecondary // Branco
        ),
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}

@Composable
fun PlaceholderScreen(name: String) {
    Scaffold(
        content = { padding ->
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
    )
}
