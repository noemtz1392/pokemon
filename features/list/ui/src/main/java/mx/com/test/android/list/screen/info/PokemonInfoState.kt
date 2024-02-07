package mx.com.test.android.list.screen.info

import mx.com.test.android.list.PokemonItem

data class PokemonInfoState(
    val pokemonItem: PokemonItem? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)