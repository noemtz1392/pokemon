package mx.com.test.android.list.api

import mx.com.test.android.list.api.model.PokemonInfoApi
import mx.com.test.android.list.api.model.PokemonListApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): Response<PokemonListApi>

    @GET("pokemon/{id}")
    suspend fun getPokemonInfo(
        @Path("id") id: Int
    ): Response<PokemonInfoApi>

}