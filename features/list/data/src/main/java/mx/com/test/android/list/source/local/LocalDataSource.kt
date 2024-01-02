package mx.com.test.android.list.source.local

interface LocalDataSource {
    suspend fun addToFavorite(id: Int, isFavorite: Boolean): Boolean
    suspend fun removeToFavorite(id: Int, isFavorite: Boolean): Boolean
}