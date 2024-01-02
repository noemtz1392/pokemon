package mx.com.test.android.list.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import mx.com.test.android.domain.common.Result
import mx.com.test.android.list.model.Pokemon

interface PokemonRepository {
    fun getPokemonList(offset: Int, limit: Int): Flow<PagingData<Pokemon>>

    suspend fun addToFavorite(pokemon: Pokemon): Result<Boolean>

    suspend fun removeToFavorite(pokemon: Pokemon): Result<Boolean>

    suspend fun getPokemonInfoById(id: Int): Result<Pokemon>
}