package mx.com.test.android.list.source.remote

import mx.com.test.android.domain.common.Result
import mx.com.test.android.list.api.model.PokemonInfoApi

interface RemoteDataSource {
    suspend fun getPokemonList(offset: Int, limit: Int): Result<List<PokemonInfoApi>>
}