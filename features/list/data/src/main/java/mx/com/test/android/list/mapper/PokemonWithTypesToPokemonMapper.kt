package mx.com.test.android.list.mapper

import mx.com.test.android.domain.common.Mapper
import mx.com.test.android.list.db.entities.relations.PokemonWithTypes
import mx.com.test.android.list.model.Pokemon
import javax.inject.Inject

class PokemonWithTypesToPokemonMapper @Inject constructor(
    private val pokemonTypeEntityToTypeListMapper: PokemonTypeEntityToTypeListMapper
) : Mapper<PokemonWithTypes, Pokemon>() {
    override fun mapFrom(from: PokemonWithTypes): Pokemon {
        return Pokemon(
            id = from.pokemon.id,
            name = from.pokemon.name,
            imageUrl = from.pokemon.imageUrl,
            height = from.pokemon.height,
            weight = from.pokemon.weight,
            types = pokemonTypeEntityToTypeListMapper.mapFromList(from.types).reversed(),
            isFavorite = from.pokemon.isFavorite
        )
    }
}