package mx.com.test.android.list.mapper

import mx.com.test.android.domain.common.Mapper
import mx.com.test.android.list.db.entities.PokemonEntity
import mx.com.test.android.list.model.Pokemon

class PokemonToPokemonEntityMapper : Mapper<Pokemon, PokemonEntity>() {
    override fun mapFrom(from: Pokemon): PokemonEntity {
        return PokemonEntity(
            id = from.id,
            name = from.name,
            imageUrl = from.imageUrl,
            shinyImageUrl = "",
            defaultImageUrl = "",
            isFavorite = from.isFavorite
        )
    }
}