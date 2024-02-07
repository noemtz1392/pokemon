package mx.com.test.android.list.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import mx.com.test.android.data.dao.BaseDao
import mx.com.test.android.list.db.entities.PokemonEntity
import mx.com.test.android.list.db.entities.relations.PokemonTypesCrossRef
import mx.com.test.android.list.db.entities.relations.PokemonWithTypes

@Dao
interface PokemonDao : BaseDao<PokemonEntity> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonTypeCrossRef(crossRef: PokemonTypesCrossRef): Long

    @Transaction
    @Query("SELECT * FROM Pokemon")
    fun pagingSource(): PagingSource<Int, PokemonWithTypes>

    @Transaction
    @Query("SELECT * FROM Pokemon")
    fun getPokemonWithType(): List<PokemonWithTypes>

    @Transaction
    @Query("SELECT * FROM Pokemon WHERE pokemon_id=:id")
    fun getPokemonInfo(id: Int): Flow<PokemonWithTypes>

    @Query("UPDATE Pokemon SET favorite=:isFavorite WHERE pokemon_id=:id")
    suspend fun updateFavorite(id: Int, isFavorite: Boolean): Int


    @Query("DELETE FROM Pokemon")
    suspend fun deleteAll()
}