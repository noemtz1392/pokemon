package mx.com.test.android.list.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Pokemon",
    indices = [(Index("pokemon_id", unique = true))]
)
data class PokemonEntity(

    @PrimaryKey
    @ColumnInfo(name = "pokemon_id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,

    @ColumnInfo(name = "shiny_image_url")
    val shinyImageUrl: String,

    @ColumnInfo(name = "front_default")
    val defaultImageUrl: String,

    @ColumnInfo(name = "height")
    val height: Int = 0,

    @ColumnInfo(name = "weight")
    val weight: Int = 0,

    @ColumnInfo(name = "favorite")
    val isFavorite: Boolean = false,
)