package mx.com.test.android.list.db.entities.relations

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["pokemon_id", "pokemon_type_id"])
data class PokemonTypesCrossRef(

    @ColumnInfo(name = "pokemon_id")
    val pokemonId: Int,

    @ColumnInfo(name = "pokemon_name")
    val pokemonName: String,

    @ColumnInfo(name = "pokemon_type_id")
    val pokemonTypeId: Int,

    @ColumnInfo(name = "pokemon_type")
    val pokemonType: String,
)