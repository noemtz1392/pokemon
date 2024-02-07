package mx.com.test.android.list.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import mx.com.test.android.list.db.entities.relations.PokemonWithTypes
import mx.com.test.android.list.mapper.PokemonWithTypesToPokemonMapper
import mx.com.test.android.list.model.Pokemon
import mx.com.test.android.list.source.local.LocalDataSource
import javax.inject.Inject

class PokemonDataRepository @Inject constructor(
    private val pager: Pager<Int, PokemonWithTypes>,
    private val pokemonWithTypesToPokemonMapper: PokemonWithTypesToPokemonMapper,
    private val localDataSource: LocalDataSource,
) : PokemonRepository {

    override fun getPokemonList(): Flow<PagingData<Pokemon>> {
        return pager.flow.map { data ->
            data.map { pokemonEntity ->
                pokemonWithTypesToPokemonMapper.mapFrom(pokemonEntity)
            }
        }
    }

    override fun getPokemonInfoById(id: Int): Flow<Pokemon?> {
        return localDataSource.getPokemonInfoById(id)
    }

    override fun updateFavorite(id: Int, isFavorite: Boolean): Flow<Boolean> =
        callbackFlow {
            trySend(true)
        }
}