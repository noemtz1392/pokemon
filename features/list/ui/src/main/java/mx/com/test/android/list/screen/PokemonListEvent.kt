package mx.com.test.android.list.screen

import mx.com.test.android.list.PokemonItem

sealed class PokemonListEvent {
    data object GetPokemonList : PokemonListEvent()
    data class AddToFavorites(val pokemon: PokemonItem) : PokemonListEvent()
    data class RemoveToFavorites(val pokemon: PokemonItem) : PokemonListEvent()
}