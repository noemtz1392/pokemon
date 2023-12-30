package mx.com.test.android.list.source.remote

import mx.com.test.android.data.network.NetworkResult
import mx.com.test.android.data.network.safeApiCall
import mx.com.test.android.domain.common.Result
import mx.com.test.android.list.api.ApiService
import mx.com.test.android.list.api.model.PokemonApi
import mx.com.test.android.list.api.model.PokemonInfoApi
import javax.inject.Inject

class PokemonApiDataSource @Inject constructor(
    private val apiService: ApiService
) : PokemonRemoteDataSource {
    override suspend fun getPokemonList(offset: Int, limit: Int): Result<List<PokemonApi>> {
        val response = safeApiCall {
            apiService.getPokemonList(offset = offset, limit = offset)
        }
        return when (response) {
            is NetworkResult.Error -> Result.Failure(Exception(response.message))
            is NetworkResult.Failure -> Result.Failure(response.exception)
            is NetworkResult.Success -> Result.fromNullable(response.data.results)
        }
    }

    override suspend fun getPokemonInfo(id: Int): Result<PokemonInfoApi> {
        val response = safeApiCall {
            apiService.getPokemonInfo(id = id)
        }
        return when (response) {
            is NetworkResult.Error -> Result.Failure(Exception(response.message))
            is NetworkResult.Failure -> Result.Failure(response.exception)
            is NetworkResult.Success -> Result.fromNullable(response.data)
        }
    }
}