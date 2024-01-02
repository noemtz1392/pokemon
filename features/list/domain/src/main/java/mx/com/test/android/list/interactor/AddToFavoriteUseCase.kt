package mx.com.test.android.list.interactor

import kotlinx.coroutines.CoroutineDispatcher
import mx.com.test.android.domain.ExceptionHandler
import mx.com.test.android.domain.UseCase
import mx.com.test.android.domain.common.Result
import mx.com.test.android.list.model.Pokemon
import mx.com.test.android.list.repository.PokemonRepository

class AddToFavoriteUseCase(
    private val pokemonRepository: PokemonRepository,
    exceptionHandler: ExceptionHandler,
    dispatcher: CoroutineDispatcher,
) : UseCase<Pokemon, Boolean>(
    exceptionHandler, dispatcher
) {

    override suspend fun execute(param: Pokemon): Result<Boolean> {
        return pokemonRepository.addToFavorite(pokemon = param)
    }
}