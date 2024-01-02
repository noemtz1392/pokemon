package mx.com.test.android.list.source.local

import mx.com.test.android.list.db.dao.PokemonDao
import javax.inject.Inject

class RoomLocalDataSource @Inject constructor(
    private val pokemonDao: PokemonDao
) : LocalDataSource {

    override suspend fun addToFavorite(id: Int, isFavorite: Boolean): Boolean {
        return pokemonDao.addToFavorite(id = id, isFavorite = isFavorite) > 0
    }

    override suspend fun removeToFavorite(id: Int, isFavorite: Boolean): Boolean {
        return pokemonDao.removeToFavorite(id = id, isFavorite = isFavorite) > 0
    }
}