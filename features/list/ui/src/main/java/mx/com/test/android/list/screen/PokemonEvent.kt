package mx.com.test.android.list.screen

import mx.com.test.android.list.PokemonItem

sealed class PokemonEvent {
    data object GetPokemonList : PokemonEvent()
    data class GetPokemonInfo(val id: Int) : PokemonEvent()
    data class AddToFavorites(val pokemon: PokemonItem) : PokemonEvent()
    data class RemoveToFavorites(val pokemon: PokemonItem) : PokemonEvent()

    data class UpdateFavorite(val pokemon: PokemonItem) : PokemonEvent()
}