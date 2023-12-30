package mx.com.test.android.list.screen

sealed class PokemonListEvent {
    data object GetPokemonList : PokemonListEvent()
    data class AddToFavorites(val id: Int) : PokemonListEvent()
    data class RemoveToFavorites(val id: Int) : PokemonListEvent()
}