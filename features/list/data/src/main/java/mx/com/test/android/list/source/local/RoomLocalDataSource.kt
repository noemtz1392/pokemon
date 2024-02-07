package mx.com.test.android.list.source.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mx.com.test.android.list.db.dao.PokemonDao
import mx.com.test.android.list.mapper.PokemonWithTypesToPokemonMapper
import mx.com.test.android.list.model.Pokemon

class RoomLocalDataSource(
    private val pokemonDao: PokemonDao,
    private val pokemonWithTypesToPokemonMapper: PokemonWithTypesToPokemonMapper,
) : LocalDataSource {

    override fun getPokemonInfoById(id: Int): Flow<Pokemon?> {
        return pokemonDao.getPokemonInfo(id).map { pokemonWithTypesToPokemonMapper.mapFrom(it) }
    }


    override suspend fun updateFavorite(id: Int, isFavorite: Boolean): Long =
        pokemonDao.updateFavorite(id = id, isFavorite = isFavorite).toLong()

}