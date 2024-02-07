package mx.com.test.android.list.interactor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import mx.com.test.android.domain.ExceptionHandler
import mx.com.test.android.domain.FlowUseCase
import mx.com.test.android.list.model.Pokemon
import mx.com.test.android.list.repository.PokemonRepository

class GetPokemonInfoByIdUseCase(
    private val pokemonRepository: PokemonRepository,
    exceptionHandler: ExceptionHandler,
    dispatcher: CoroutineDispatcher,
) : FlowUseCase<Int, Pokemon?>(exceptionHandler, dispatcher) {
    override fun execute(params: Int): Flow<Pokemon?> {
        return pokemonRepository.getPokemonInfoById(id = params)
    }
}