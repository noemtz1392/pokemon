package mx.com.test.android.list.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import mx.com.test.android.list.model.Pokemon

interface PokemonRepository {
    fun getPokemonList(): Flow<PagingData<Pokemon>>

    fun getPokemonInfoById(id: Int): Flow<Pokemon?>

    fun updateFavorite(id: Int, isFavorite: Boolean): Flow<Boolean>
}