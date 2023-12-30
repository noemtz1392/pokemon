package mx.com.test.android.list.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import mx.com.test.android.list.model.Pokemon

interface PokemonRepository {
    fun getPokemonList(offset: Int, limit: Int): Flow<PagingData<Pokemon>>
}