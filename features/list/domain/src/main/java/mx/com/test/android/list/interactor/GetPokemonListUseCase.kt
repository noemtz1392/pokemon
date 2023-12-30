package mx.com.test.android.list.interactor

import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import mx.com.test.android.domain.ExceptionHandler
import mx.com.test.android.domain.FlowUseCase
import mx.com.test.android.list.model.Pokemon
import mx.com.test.android.list.repository.PokemonRepository

typealias Offset = Int
typealias Limit = Int

class GetPokemonListUseCase(
    private val pokemonRepository: PokemonRepository,
    exceptionHandler: ExceptionHandler,
    dispatcher: CoroutineDispatcher
) : FlowUseCase<Offset, Limit, PagingData<Pokemon>>(exceptionHandler, dispatcher) {

    override fun execute(firstParam: Offset, secondParam: Limit): Flow<PagingData<Pokemon>> {
        return pokemonRepository.getPokemonList(offset = firstParam, limit = secondParam)
    }
}