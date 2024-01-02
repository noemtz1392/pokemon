package mx.com.test.android.list.mapper

import mx.com.test.android.domain.common.Mapper
import mx.com.test.android.list.PokemonItem
import mx.com.test.android.list.model.Pokemon
import javax.inject.Inject

class PokemonItemToPokemonMapper @Inject constructor() : Mapper<PokemonItem, Pokemon>() {
    override fun mapFrom(from: PokemonItem): Pokemon {
        return Pokemon(
            id = from.id,
            name = from.name,
            imageUrl = from.imageUrl,
            height = from.height,
            weight = from.weight,
            isFavorite = from.isFavorite
        )
    }
}