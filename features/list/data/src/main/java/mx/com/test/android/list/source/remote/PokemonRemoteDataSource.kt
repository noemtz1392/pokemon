package mx.com.test.android.list.source.remote

import mx.com.test.android.domain.common.Result
import mx.com.test.android.list.api.model.PokemonApi
import mx.com.test.android.list.api.model.PokemonInfoApi

interface PokemonRemoteDataSource {
    suspend fun getPokemonList(offset: Int, limit: Int): Result<List<PokemonApi>>

    suspend fun getPokemonInfo(id: Int): Result<PokemonInfoApi>
}