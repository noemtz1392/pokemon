package mx.com.test.android.list.mapper

import mx.com.test.android.domain.common.Mapper
import mx.com.test.android.list.PokemonItem
import mx.com.test.android.list.model.Pokemon

class PokemonToPokemonItemMapper : Mapper<Pokemon, PokemonItem>() {
    override fun mapFrom(from: Pokemon): PokemonItem {
        return PokemonItem(
            id = from.id,
            name = from.name,
            imageUrl = from.imageUrl,
            height = from.height,
            weight = from.weight,
            types = from.types,
            isFavorite = from.isFavorite
        )
    }
}