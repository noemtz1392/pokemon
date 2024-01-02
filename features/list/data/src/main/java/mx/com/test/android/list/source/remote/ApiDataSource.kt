package mx.com.test.android.list.source.remote

import mx.com.test.android.data.network.NetworkResult
import mx.com.test.android.data.network.safeApiCall
import mx.com.test.android.domain.common.Result
import mx.com.test.android.list.api.ApiService
import mx.com.test.android.list.api.model.PokemonInfoApi
import javax.inject.Inject

class ApiDataSource @Inject constructor(
    private val apiService: ApiService
) : RemoteDataSource {
    override suspend fun getPokemonList(offset: Int, limit: Int): Result<List<PokemonInfoApi>> {
        val response = safeApiCall {
            apiService.getPokemonList(offset = offset, limit = limit)
        }

        return when (response) {
            is NetworkResult.Error -> Result.Failure(Exception(response.message))
            is NetworkResult.Failure -> Result.Failure(response.exception)
            is NetworkResult.Success -> {
                val pokemonList = response.data.results.map {
                    val infoResponse = safeApiCall {
                        apiService.getPokemonInfo(id = it.getId())
                    }
                    (infoResponse as NetworkResult.Success).data.copy(id = it.getId())
                }
                return Result.fromNullable(pokemonList)
            }
        }
    }
}