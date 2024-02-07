package mx.com.test.android.list.navigation

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

class NavigationActions(navController: NavHostController) {
    val openPokemonInfo = { id: Int, from: NavBackStackEntry ->
        if (from.lifecycleIsResumed()) {
            navController.navigate(NavigationRoutes.Authenticated.PokemonInfo.createRoute(id))
        }
    }
}

fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED