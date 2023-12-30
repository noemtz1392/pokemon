package mx.com.test.android.list.mapper

import mx.com.test.android.domain.common.Mapper
import mx.com.test.android.list.api.model.PokemonApi
import mx.com.test.android.list.db.entities.PokemonEntity

class PokemonApiToPokemonEntityMapper : Mapper<PokemonApi, PokemonEntity>() {
    override fun mapFrom(from: PokemonApi): PokemonEntity {
        return PokemonEntity(
            id = from.getId(),
            name = from.name,
            frontImageUrl = from.url,
            height = 0,
            weight = 0
        )
    }
}