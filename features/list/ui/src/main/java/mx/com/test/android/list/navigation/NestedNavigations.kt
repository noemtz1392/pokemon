package mx.com.test.android.list.navigation

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import mx.com.test.android.list.screen.PokemonListEvent
import mx.com.test.android.list.screen.PokemonListViewModel
import mx.com.test.android.list.screen.PokemonScreen
import mx.com.test.android.list.screen.info.PokemonInfoScreen
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
    viewModel: PokemonListViewModel
) {
    navigation(
        route = NavigationRoutes.Authenticated.NavigationRoute.route,
        startDestination = NavigationRoutes.Authenticated.PokemonList.route
    ) {
        composable(route = NavigationRoutes.Authenticated.PokemonList.route) { backStackEntry ->
            PokemonScreen(
                viewModel = viewModel,
                addToFavorite = { pokemon ->
                    viewModel.onEvent(PokemonListEvent.AddToFavorites(pokemon))
                },
                removeToFavorite = { pokemon ->
                    viewModel.onEvent(PokemonListEvent.RemoveToFavorites(pokemon))
                },
                navigateToPokemonInfo = {
                    if (backStackEntry.lifecycleIsResumed()) {
                        navController.navigate(
                            NavigationRoutes.Authenticated.PokemonInfo.createRoute(
                                id
                            )
                        )
                    }
                }
            )
        }
        composable(
            route = NavigationRoutes.Authenticated.PokemonInfo.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            PokemonInfoScreen(viewModel = viewModel)
        }
    }
}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED