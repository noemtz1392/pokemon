package mx.com.test.android.list.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import mx.com.test.android.list.screen.PokemonEvent
import mx.com.test.android.list.screen.PokemonViewModel
import mx.com.test.android.list.screen.info.PokemonInfoScreen
import mx.com.test.android.list.screen.info.PokemonInfoViewModel
import mx.com.test.android.list.screen.list.PokemonScreen
import mx.com.test.android.list.screen.onboarding.OnboardingScreen

fun NavGraphBuilder.unauthenticatedGraph(navController: NavController) {
    navigation(
        route = NavigationRoutes.Unauthenticated.NavigationRoute.route,
        startDestination = NavigationRoutes.Unauthenticated.NavigationRoute.route
    ) {
        composable(route = NavigationRoutes.Unauthenticated.Onboarding.route) { backStackEntry ->
            OnboardingScreen {
                if (backStackEntry.lifecycleIsResumed()) {
                    navController.navigate(NavigationRoutes.Authenticated.PokemonList.route)
                }
            }
        }
    }
}

fun NavGraphBuilder.authenticatedGraph(
    navController: NavController,
    viewModel: PokemonViewModel,
    actions: NavigationActions
) {
    navigation(
        route = NavigationRoutes.Authenticated.NavigationRoute.route,
        startDestination = NavigationRoutes.Authenticated.PokemonList.route
    ) {
        composable(route = NavigationRoutes.Authenticated.PokemonList.route) { backStackEntry ->
            PokemonScreen(
                viewModel = viewModel,
                addToFavorite = { viewModel.onEvent(PokemonEvent.UpdateFavorite(it)) },
                removeToFavorite = { },
                navigateToPokemonInfo = { id -> actions.openPokemonInfo(id, backStackEntry) }
            )
        }
        composable(
            route = NavigationRoutes.Authenticated.PokemonInfo.route,
            arguments = listOf(
                navArgument(Routes.PARAM_POKEMON_ID) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val viewModelInfo = hiltViewModel<PokemonInfoViewModel>()
            val arguments = requireNotNull(backStackEntry.arguments)
            val pokemonId = arguments.getInt(Routes.PARAM_POKEMON_ID)

            PokemonInfoScreen(
                viewModel = viewModelInfo,
                addToFavorite = { viewModelInfo.updateFavorite(it) },
                removeToFavorite = { },
                navigateToBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}