package mx.com.test.android.list.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import mx.com.test.android.data.dao.BaseDao
import mx.com.test.android.list.db.entities.PokemonEntity

@Dao
interface PokemonDao : BaseDao<PokemonEntity> {

    @Query("SELECT * FROM Pokemon")
    fun pagingSource(): PagingSource<Int, PokemonEntity>

    @Query("DELETE FROM Pokemon")
    suspend fun deleteAll()
}