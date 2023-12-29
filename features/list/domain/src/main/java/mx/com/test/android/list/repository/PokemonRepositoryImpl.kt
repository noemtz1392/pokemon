package mx.com.test.android.list.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mx.com.test.android.domain.common.Result
import mx.com.test.android.list.model.Pokemon

class PokemonRepositoryImpl:PokemonRepository {
    override fun getPokemonList(): Flow<Result<List<Pokemon>>> = flow {

    }
}