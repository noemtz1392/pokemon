package mx.com.test.android.list.db.dao

import androidx.room.Dao
import androidx.room.Query
import mx.com.test.android.data.dao.BaseDao
import mx.com.test.android.list.db.entities.PokemonTypeEntity

@Dao
interface PokemonTypeDao : BaseDao<PokemonTypeEntity> {
    @Query("DELETE FROM PokemonType")
    suspend fun deleteAll()
}