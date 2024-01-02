package mx.com.test.android.list.db.dao

import androidx.room.Dao
import androidx.room.Query
import mx.com.test.android.data.dao.BaseDao
import mx.com.test.android.list.db.entities.RemoteKeysEntity

@Dao
interface RemoteKeysDao : BaseDao<RemoteKeysEntity> {
    @Query("SELECT * FROM RemoteKeys")
    suspend fun getRemoteKeys(): List<RemoteKeysEntity?>

    @Query("DELETE FROM RemoteKeys")
    suspend fun deleteAll()

    @Query("SELECT created_at FROM RemoteKeys ORDER BY created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}