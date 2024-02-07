package mx.com.test.android.list.navigation

sealed class NavigationRoutes {

    sealed class Unauthenticated(val route: String) : NavigationRoutes() {
        data object NavigationRoute : Unauthenticated(route = "unauthenticated")

        data object Onboarding : Unauthenticated(route = "onboarding")
    }

    sealed class Authenticated(val route: String) : NavigationRoutes() {
        data object NavigationRoute : Authenticated(route = "authenticated")

        data object PokemonList : Authenticated(route = "pokemon_list")
        data object PokemonInfo : Authenticated(route = "pokemon_info/{${Routes.PARAM_POKEMON_ID}}") {
            fun createRoute(id: Int) = "pokemon_info/$id"
        }
    }
}


object Routes {
    const val PARAM_POKEMON_ID = "id"
}