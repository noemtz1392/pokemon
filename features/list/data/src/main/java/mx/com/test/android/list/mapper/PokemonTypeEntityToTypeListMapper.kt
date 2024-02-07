package mx.com.test.android.list.mapper

import mx.com.test.android.domain.common.Mapper
import mx.com.test.android.list.db.entities.PokemonTypeEntity

class PokemonTypeEntityToTypeListMapper : Mapper<PokemonTypeEntity, String>() {
    override fun mapFrom(from: PokemonTypeEntity): String {
        return from.name
    }
}