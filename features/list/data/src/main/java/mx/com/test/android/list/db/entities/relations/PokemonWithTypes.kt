package mx.com.test.android.list.db.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import mx.com.test.android.list.db.entities.PokemonEntity
import mx.com.test.android.list.db.entities.PokemonTypeEntity

data class PokemonWithTypes(

    @Embedded
    val pokemon: PokemonEntity,

    @Relation(
        parentColumn = "pokemon_id",
        entityColumn = "pokemon_type_id",
        associateBy = Junction(PokemonTypesCrossRef::class)
    )
    val types: List<PokemonTypeEntity>
)