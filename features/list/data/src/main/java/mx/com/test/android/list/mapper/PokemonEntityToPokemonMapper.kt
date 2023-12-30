package mx.com.test.android.list.mapper

import mx.com.test.android.domain.common.Mapper
import mx.com.test.android.list.db.entities.PokemonEntity
import mx.com.test.android.list.model.Pokemon

class PokemonEntityToPokemonMapper:Mapper<PokemonEntity, Pokemon>() {
    override fun mapFrom(from: PokemonEntity): Pokemon {
        return Pokemon(
            id = from.id,
            name = from.name,
            imageUrl = from.frontImageUrl
        )
    }
}