package mx.com.test.android.list.screen

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import mx.com.test.android.list.PokemonItem

@Parcelize
data class PokemonListState(
    val pokemonList: List<PokemonItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) : Parcelable