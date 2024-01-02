package mx.com.test.android.list.interactor

import kotlinx.coroutines.CoroutineDispatcher
import mx.com.test.android.domain.ExceptionHandler
import mx.com.test.android.domain.UseCase
import mx.com.test.android.domain.common.Result
import mx.com.test.android.list.model.Pokemon
import mx.com.test.android.list.repository.PokemonRepository

class GetPokemonInfoById(
    private val pokemonRepository: PokemonRepository,
    exceptionHandler: ExceptionHandler,
    dispatcher: CoroutineDispatcher,
) : UseCase<Int, Pokemon>(exceptionHandler, dispatcher) {
    override suspend fun execute(param: Int): Result<Pokemon> {
        return pokemonRepository.getPokemonInfoById(id = param)
    }
}