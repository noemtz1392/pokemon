package mx.com.test.android.list.mapper

import mx.com.test.android.domain.common.Mapper
import mx.com.test.android.list.api.model.TypeApi
import mx.com.test.android.list.db.entities.PokemonTypeEntity

class PokemonTypeApiToPokemonTypeEntityMapper : Mapper<TypeApi, PokemonTypeEntity>() {
    override fun mapFrom(from: TypeApi): PokemonTypeEntity {
        return PokemonTypeEntity(
            id = from.getId(),
            name = from.name,
        )
    }
}