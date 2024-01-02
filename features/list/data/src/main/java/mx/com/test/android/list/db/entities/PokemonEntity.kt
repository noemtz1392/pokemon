package mx.com.test.android.list.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Pokemon",
    indices = [Index("id", unique = true)]
)
data class PokemonEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,

    @ColumnInfo(name = "height")
    val height: Int = 0,

    @ColumnInfo(name = "weight")
    val weight: Int = 0,

    @ColumnInfo(name = "favorite")
    val isFavorite: Boolean = false
)