package mx.com.test.android.list.mapper

import mx.com.test.android.domain.common.Mapper
import mx.com.test.android.list.api.model.PokemonInfoApi
import mx.com.test.android.list.db.entities.PokemonEntity

class PokemonApiToPokemonEntityMapper : Mapper<PokemonInfoApi, PokemonEntity>() {
    override fun mapFrom(from: PokemonInfoApi): PokemonEntity {
        return PokemonEntity(
            id = from.id,
            name = from.name,
            imageUrl = from.sprites.otherSprite.homeSprite.imageUrl,
            shinyImageUrl = from.sprites.otherSprite.homeSprite.shinyImageUrl,
            defaultImageUrl = from.sprites.defaultImageUrl,
            height = from.height,
            weight = from.weight,
        )
    }
}