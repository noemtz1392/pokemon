package mx.com.test.android.list.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import mx.com.test.android.list.screen.PokemonListViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: String = NavigationRoutes.Authenticated.NavigationRoute.route,
) {
    val viewModel: PokemonListViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        //unauthenticatedGraph(navController = navController)
        authenticatedGraph(navController = navController, viewModel = viewModel)
    }
}