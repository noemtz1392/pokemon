package mx.com.test.android.list

import mx.com.test.android.domain.common.Mapper
import mx.com.test.android.list.model.Pokemon
import javax.inject.Inject

class PokemonToPokemonItemMapper @Inject constructor(): Mapper<Pokemon, PokemonItem>() {
    override fun mapFrom(from: Pokemon): PokemonItem {
        return PokemonItem(
            id = from.id,
            name = from.name,
            imageUrl = from.imageUrl
        )
    }
}