package mx.com.test.android.list.source.local

import kotlinx.coroutines.flow.Flow
import mx.com.test.android.list.model.Pokemon

interface LocalDataSource {
    fun getPokemonInfoById(id: Int): Flow<Pokemon?>

    suspend fun updateFavorite(id: Int, isFavorite: Boolean): Long
}