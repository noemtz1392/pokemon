package mx.com.test.android.list.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import mx.com.test.android.list.screen.PokemonViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: String = NavigationRoutes.Authenticated.NavigationRoute.route,
) {
    val actions = remember(navController) { NavigationActions(navController) }
    val viewModel: PokemonViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        authenticatedGraph(navController = navController, viewModel = viewModel, actions = actions)
    }
}