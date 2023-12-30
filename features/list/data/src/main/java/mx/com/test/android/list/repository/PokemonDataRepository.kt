package mx.com.test.android.list.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mx.com.test.android.list.db.entities.PokemonEntity
import mx.com.test.android.list.mapper.PokemonEntityToPokemonMapper
import mx.com.test.android.list.model.Pokemon
import javax.inject.Inject

class PokemonDataRepository @Inject constructor(
    private val pager: Pager<Int, PokemonEntity>,
    private val pokemonEntityToPokemonMapper: PokemonEntityToPokemonMapper
) : PokemonRepository {

    override fun getPokemonList(offset: Int, limit: Int): Flow<PagingData<Pokemon>> {
        return pager.flow.map { data ->
            data.map { pokemonEntity ->
                pokemonEntityToPokemonMapper.mapFrom(pokemonEntity)
            }
        }
    }
}