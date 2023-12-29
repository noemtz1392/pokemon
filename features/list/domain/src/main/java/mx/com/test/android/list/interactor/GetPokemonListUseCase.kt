package mx.com.test.android.list.interactor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import mx.com.test.android.domain.ExceptionHandler
import mx.com.test.android.domain.FlowUseCase
import mx.com.test.android.domain.common.Result
import mx.com.test.android.list.model.Pokemon
import mx.com.test.android.list.repository.PokemonRepository

class GetPokemonListUseCase(
    private val pokemonRepository: PokemonRepository,
    exceptionHandler: ExceptionHandler,
    dispatcher: CoroutineDispatcher
) : FlowUseCase<String, List<Pokemon>>(exceptionHandler, dispatcher) {
    override suspend fun execute(params: String): Flow<Result<List<Pokemon>>> {
        return pokemonRepository.getPokemonList()
    }
}