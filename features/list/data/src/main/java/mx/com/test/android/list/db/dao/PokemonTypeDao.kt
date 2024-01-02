package mx.com.test.android.list.db.dao

import androidx.room.Dao
import androidx.room.Query
import mx.com.test.android.data.dao.BaseDao
import mx.com.test.android.list.db.entities.PokemonType

@Dao
interface PokemonTypeDao : BaseDao<PokemonType> {
    @Query("DELETE FROM PokemonType")
    suspend fun deleteAll()
}