package mx.com.test.android.list.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonListApi(
    val results: List<PokemonApi>
)

@JsonClass(generateAdapter = true)
data class PokemonApi(
    val name: String,
    val url: String
) {
    fun getId(): Int {
        val pattern = """/(\d+)/?$""".toRegex()
        val match = pattern.find(url)
        val pokemonNumber = match?.value?.substringBeforeLast("/")?.substringAfter("/") ?: "0"
        return try {
            pokemonNumber.toInt()
        } catch (ex: NumberFormatException) {
            0
        }
    }
}

@JsonClass(generateAdapter = true)
data class PokemonInfoApi(
    val id: Int,
    val name: String,
    @Json(name = "sprites")
    val sprites: SpritesApi,
    val height: Int,
    val weight: Int,
    //@Json(name = "types")
    //val types: List<PokemonTypeApi>?
)

@JsonClass(generateAdapter = true)
data class SpritesApi(
    @Json(name = "front_default")
    val frontDefault: String
)


@JsonClass(generateAdapter = true)
data class PokemonTypeApi(
    @Json(name = "type")
    val type: TypeApi
)

@JsonClass(generateAdapter = true)
data class TypeApi(
    @Json(name = "name")
    val name: String
)

