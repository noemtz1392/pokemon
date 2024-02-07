package mx.com.test.android.list.interactor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import mx.com.test.android.list.repository.PokemonRepository

class UpdateFavoriteUseCase(
    private val pokemonRepository: PokemonRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke(id: Int, isFavorite: Boolean): Flow<Boolean> =
        pokemonRepository.updateFavorite(id = id, isFavorite = isFavorite)
            .flowOn(ioDispatcher)

}