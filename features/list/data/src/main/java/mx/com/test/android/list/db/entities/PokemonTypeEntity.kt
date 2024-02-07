package mx.com.test.android.list.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "PokemonType",
    indices = [Index("pokemon_type_id", unique = true)]
)
data class PokemonTypeEntity(

    @PrimaryKey
    @ColumnInfo(name = "pokemon_type_id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,
)