package mx.com.test.android.list.repository

import kotlinx.coroutines.flow.Flow
import mx.com.test.android.domain.common.Result
import mx.com.test.android.list.model.Pokemon

fun interface PokemonRepository {
    fun getPokemonList(): Flow<Result<List<Pokemon>>>
}